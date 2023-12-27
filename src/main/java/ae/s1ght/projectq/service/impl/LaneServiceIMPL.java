package ae.s1ght.projectq.service.impl;

import ae.s1ght.projectq.dao.LaneRepository;
import ae.s1ght.projectq.enums.LaneStatus;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.exception.AlreadyAssignedPump;
import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.LaneServiceINT;
import ae.s1ght.projectq.service.PumpServiceINT;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class LaneServiceIMPL implements LaneServiceINT {

    private final LaneRepository laneRepository;
    private final PumpServiceIMPL pumpService;
    private static final Logger logger = LoggerFactory.getLogger(LaneServiceIMPL.class);

    @Override
    public Lane getLaneByPlate(String plate) {
        return laneRepository.findLaneByVehicleLicensePlate(plate)
                .orElseThrow(() -> new EntityNotFoundException("Lane not found for vehicle with plate " + plate));
    }

    @Override
    public Lane saveLane(Lane lane) {
        return laneRepository.save(lane);
    }

    @Override
    public void deleteLane(Long id) {

    }

    @Override
    @Transactional
    public void updateLaneStatus(Lane lane) {
        int queueLength = lane.getVehicleQueue().size();
        int availablePumps = pumpService.calculateAvailablePumps(lane.getPumps());
        logger.debug(lane.getId()+" has:");
        logger.debug("Queue Length: "+queueLength);
        logger.debug("available Pumps: "+availablePumps);
        if (queueLength < availablePumps) {
            lane.setStatus(LaneStatus.IDLE);
        } else if (queueLength >= availablePumps ) {
            lane.setStatus(LaneStatus.BUSY);
        } else if (queueLength >= 3){
            lane.setStatus(LaneStatus.CONGESTED);
        }
        logger.debug("Saved Lane: ", lane);
        laneRepository.save(lane); // Save the updated lane status
    }

    @Override
    public void addVehicleToQueue(Lane lane, Vehicle vehicle) {
        lane.getVehicleQueue().add(vehicle);
        logger.debug("Added to queue: ", vehicle, lane);
        updateLaneStatus(lane);
    }

    @Override
    public void removeVehicleFromQueue(Lane lane, Vehicle vehicle) {
        lane.getVehicleQueue().remove(vehicle);
        updateLaneStatus(lane);
    }

    @Override
    public double calculateAccDelay(Lane lane) {
        double accumulatedDelay = 0;
        for (Pump pump : lane.getPumps()) {
            accumulatedDelay += pumpService.calculateApproxTTC(pump);
        }
        return accumulatedDelay;
    }

    @Override
    @Transactional
    public void assignVehicleToPumpIfAvailable(Lane lane) {
        if (!lane.getVehicleQueue().isEmpty()) {
            Pump availablePump = pumpService.findAvailablePump(lane);
            if (availablePump != null) {
                Vehicle vehicleToAssign = lane.getVehicleQueue().get(0); // Get and remove the first vehicle in the queue
                lane.getVehicleQueue().remove(0);
                pumpService.assignVehicleToPump(vehicleToAssign, availablePump);
                logger.debug(vehicleToAssign.getLicensePlate()+" assigned to " + availablePump.getId());
                updateLaneStatus(lane);
            }
        }
    }

    @Override
    public void vehicleFinishedFueling(Pump pump) {
        pump.setStatus(PumpStatus.IDLE); // Set pump status to IDLE
        pump.setVehicle(null); // Remove the vehicle from the pump
        pumpService.savePump(pump);
        assignVehicleToPumpIfAvailable(pump.getLane()); // Check if there is a waiting vehicle in the lane
    }

    @Override
    @Transactional
    public Lane assignVehicleToOptimalSpot(Vehicle vehicle) {
        if(vehicle.getAssignedPump() != null) throw new AlreadyAssignedPump("[ERROR] Assigning vehicle a pump as it already has one");

        List<Lane> lanes = laneRepository.findAll(); // Get all lanes
        Lane optimalLane = null;

        for (Lane lane : lanes) {
            if (lane.getStatus() == LaneStatus.IDLE) {
                optimalLane = lane;
                break; // Break the loop as we found an idle lane
            }
        }

        if (optimalLane == null) {
            final double BIAS = 50;
            final double averageDelayPerVehicle = 120;
            double minDelay = Double.MAX_VALUE;
            for (Lane lane : lanes) {
                double currentAccumulatedDelay = calculateAccDelay(lane) + BIAS;
                int queueLength = lane.getVehicleQueue().size(); // Assuming this returns the queue length

                // Factor in both the accumulated delay and queue length
                double totalDelayEstimate = currentAccumulatedDelay + (queueLength * averageDelayPerVehicle);

                if (totalDelayEstimate < minDelay) {
                    minDelay = totalDelayEstimate;
                    optimalLane = lane;
                }
            }
        }

        if (optimalLane != null) {
            addVehicleToQueue(optimalLane, vehicle);
        }

        return optimalLane;
    }
}
