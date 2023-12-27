package ae.s1ght.projectq.service.impl;

import ae.s1ght.projectq.dao.LaneRepository;
import ae.s1ght.projectq.dao.PumpRepository;
import ae.s1ght.projectq.dao.VehicleRepository;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.exception.PumpNotReserved;
import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.PumpServiceINT;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PumpServiceIMPL implements PumpServiceINT {

    private final VehicleRepository vehicleRepository;
    private final PumpRepository pumpRepository;

    @Override
    public Pump getPumpById(Long id) {
        Optional<Pump> optionalPump = pumpRepository.findById(id);
        Pump pump = optionalPump.get();
        return pump;
    }

    @Override
    public List<Pump> getAllPumps() {
        return pumpRepository.findAll();
    }


    @Override
    public Map<String, String> getReservers() {
        List<Pump> reservedPumps = pumpRepository.findAllByStatus(PumpStatus.RESERVED);
        Map<String, String> reservedPumpMap = new HashMap<>();
        for (Pump pump : reservedPumps) {
            Vehicle vehicle = pump.getVehicle();
            if (vehicle != null) {
                reservedPumpMap.put(vehicle.getLicensePlate(), String.valueOf(pump.getId()));
            }
        }
        return reservedPumpMap;
    }

    @Override
    public Pump savePump(Pump pump) {
        return pumpRepository.save(pump);
    }

    @Override
    @Transactional
    public void startFueling(Long id, double initialFuel) {
        Optional<Pump> optionalPump = pumpRepository.findById(id);
        Pump pump = optionalPump.get();
        if (!pump.getStatus().equals(PumpStatus.RESERVED)) {
            throw new PumpNotReserved("Pump is not in reserved status");
        }
        pump.setStatus(PumpStatus.DISPENSING);
        pump.setFuelInitial(initialFuel);

        pumpRepository.save(pump);

    }

    @Override
    public void deletePump(Long id) {

    }

    @Override
    public Pump findAvailablePump(Lane lane) {
        List<Pump> pumps = lane.getPumps(); // Assuming this retrieves all pumps in the lane
        for (Pump pump : pumps) {
            if (pump.getStatus() == PumpStatus.IDLE) {
                return pump; // Return the first idle pump found
            }
        }
        return null; // No available pump found in this lane
    }

    @Override
    @Transactional
    public Lane resetPump(Pump pump) {
        pump.setStatus(PumpStatus.IDLE);
        pump.setVehicle(null);
        pump.setFuelDispensed(0);
        pump.setApproximateTimeToCompletion(0);
        pump.setFuelInitial(0);
        pumpRepository.save(pump);
        Lane lane = pump.getLane();
        return lane;
    }

    @Override
    public int calculateAvailablePumps(List<Pump> pumps) {
        int availablePumps = 0;
        for (Pump pump : pumps) {
            if (pump.getStatus() == PumpStatus.IDLE) {
                availablePumps++;
            }
        }
        return availablePumps;
    }

    @Override
    public double getAmountPayable(Pump pump) {
        double dispensed = pump.getFuelDispensed();
        double rate = 3.55;
        return dispensed*rate;
    }

    @Override
    @Transactional
    public void assignVehicleToPump(Vehicle vehicle, Pump pump) {
        if (vehicle == null) {
            return;
        }
        pump.setVehicle(vehicle);
        pump.setStatus(PumpStatus.RESERVED); // Set pump status to RESERVED
        vehicle.setAssignedPump(pump); // Link the vehicle to the pump
        vehicle.setLane(pump.getLane());

        pumpRepository.save(pump);
        vehicleRepository.save(vehicle);
    }

    @Override
    public double getPercentage(Pump pump) {
        Vehicle vehicle = pump.getVehicle();
        if (vehicle == null) {
            return 0; // No vehicle at the pump
        }
        double totalFuelCapacity = vehicle.getFuelCapacity();
        double initialFuelLevel = pump.getFuelInitial();
        double fuelNeeded = totalFuelCapacity - initialFuelLevel;

        // Calculate and set the percentage completed
        double fuelDispensedSoFar = pump.getFuelDispensed();
        double percentageCompleted = (fuelDispensedSoFar / fuelNeeded) * 100;
        return percentageCompleted;
    }

    @Override
    public double calculateApproxTTC(Pump pump) {
        Vehicle vehicle = pump.getVehicle();
        if (vehicle == null) {
            return 0; // No vehicle at the pump
        }
        double totalFuelCapacity = vehicle.getFuelCapacity();
        double initialFuelLevel = pump.getFuelInitial();
        double fuelNeeded = totalFuelCapacity - initialFuelLevel;

        final double DISPENSING_RATE = 30; // Fixed rate in liters per minute

        double timeToCompletion = fuelNeeded / DISPENSING_RATE; // Time in minutes

        return (timeToCompletion * 60);
    }

    @Override
    public List<Pump> getPumpsByLaneId(Long laneId) {
        return null;
    }
}
