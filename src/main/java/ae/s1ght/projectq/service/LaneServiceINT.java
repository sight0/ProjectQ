package ae.s1ght.projectq.service;

import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;

public interface LaneServiceINT {
    Lane getLaneByPlate(String plate);
    Lane saveLane(Lane lane);
    void deleteLane(Long id);
    void updateLaneStatus(Lane lane);
    void addVehicleToQueue(Lane lane, Vehicle vehicle);
    void removeVehicleFromQueue(Lane lane, Vehicle vehicle);
    double calculateAccDelay(Lane lane);
    Lane assignVehicleToOptimalSpot(Vehicle vehicle);
    void assignVehicleToPumpIfAvailable(Lane lane);
    void vehicleFinishedFueling(Pump pump);

}
