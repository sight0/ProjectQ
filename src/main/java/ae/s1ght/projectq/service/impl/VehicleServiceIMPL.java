package ae.s1ght.projectq.service.impl;

import ae.s1ght.projectq.dao.VehicleRepository;
import ae.s1ght.projectq.exception.VehicleNotFoundException;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.VehicleServiceINT;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleServiceIMPL implements VehicleServiceINT {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle getVehicleById(Long id) {
        return null;
    }

    @Override
    public Vehicle getVehicleByPlate(String plate) throws VehicleNotFoundException{
        Vehicle vehicle = vehicleRepository.getVehicleByLicensePlate(plate)
                .orElseThrow(() ->
                        new VehicleNotFoundException(String.format("[ERROR] Vehicle with plate number '%s' not found!",plate)));
        return vehicle;
    }

    @Override
    public void linkVehicle(User user, Vehicle vehicle) {

    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return null;
    }

    @Override
    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle.getId() != null && vehicleRepository.existsById(vehicle.getId())) {
            Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicle.getId());
            if (existingVehicle.isPresent() && existingVehicle.get().equals(vehicle)) {
                return null;
            }
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> fetchDataFromPlate(String plate) {
        String line;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/vehicles.csv").getInputStream()))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].trim().equals(plate)) {
                    // Assuming data format: plate, type, manufacturer, model, year, fuelType, capacity
                    return Optional.of(new Vehicle(data[0], data[1], data[2], data[3], data[4], data[5], Double.parseDouble(data[6])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteVehicle(Long id) {

    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Long userId) {
        return null;
    }
}
