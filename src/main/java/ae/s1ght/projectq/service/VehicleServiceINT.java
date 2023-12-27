package ae.s1ght.projectq.service;

import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleServiceINT {
    Vehicle getVehicleById(Long id);
    Vehicle getVehicleByPlate(String plate);
    void linkVehicle(User user, Vehicle vehicle);

    List<Vehicle> getAllVehicles();
    Vehicle saveVehicle(Vehicle vehicle);
    Optional<Vehicle> fetchDataFromPlate(String plate);
    void deleteVehicle(Long id);
    List<Vehicle> getVehiclesByUserId(Long userId);
}
