package ae.s1ght.projectq.controller;

import ae.s1ght.projectq.enums.CameraLocation;
import ae.s1ght.projectq.exception.VehicleNotFoundException;
import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.impl.LaneServiceIMPL;
import ae.s1ght.projectq.service.impl.UserServiceIMPL;
import ae.s1ght.projectq.service.impl.VehicleServiceIMPL;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/camera")
@AllArgsConstructor
public class CameraController {

    private final VehicleServiceIMPL vehicleServiceIMPL;
    private final LaneServiceIMPL laneServiceIMPL;

    @PostMapping("/detection")
    public ResponseEntity<String> vehicleDetected(
            @RequestParam String licensePlate,
            @RequestParam CameraLocation cameraLocation
    ){
        Vehicle vehicle;
        try{
            vehicle = vehicleServiceIMPL.getVehicleByPlate(licensePlate);
        }catch (VehicleNotFoundException vnf){
            Optional<Vehicle> fetchedVehicle = vehicleServiceIMPL.fetchDataFromPlate(licensePlate);
            if (fetchedVehicle.isPresent()) {
                vehicle = vehicleServiceIMPL.saveVehicle(fetchedVehicle.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vehicle not found in external data");
            }
        }

        switch (cameraLocation) {
            case ENTRANCE:
                // Handle entrance camera logic
                Lane assignedLane = laneServiceIMPL.assignVehicleToOptimalSpot(vehicle);
                laneServiceIMPL.assignVehicleToPumpIfAvailable(assignedLane);
                // Check for available pump
                break;
            default:
                // Handle default or unknown camera location
                return ResponseEntity.badRequest().body("Unknown camera location");
        }

        return ResponseEntity.ok("Vehicle processed!");
    }
}
