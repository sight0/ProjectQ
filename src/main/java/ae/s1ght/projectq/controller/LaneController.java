package ae.s1ght.projectq.controller;

import ae.s1ght.projectq.enums.CameraLocation;
import ae.s1ght.projectq.enums.PaymentMethod;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Payment;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.service.impl.LaneServiceIMPL;
import ae.s1ght.projectq.service.impl.VehicleServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/lane")
@AllArgsConstructor
public class LaneController {
    private final VehicleServiceIMPL vehicleServiceIMPL;
    private final LaneServiceIMPL laneServiceIMPL;
    @GetMapping("/assignment")
    public ResponseEntity<?> getLane(
            @RequestParam String licensePlate
    ){
        try {
            Lane lane = laneServiceIMPL.getLaneByPlate(licensePlate);
            Map<String, Long> response = new HashMap<>();
            response.put("laneId", lane.getId());
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
