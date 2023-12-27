package ae.s1ght.projectq.controller;

import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.impl.PumpServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/pump")
@AllArgsConstructor
public class PumpController {

    private final PumpServiceIMPL pumpService;

    @PostMapping("/start")
    public ResponseEntity<String> startFueling(
            @RequestParam Long pumpId,
            @RequestParam double initialFuel
    ){
        try {
            pumpService.startFueling(pumpId, initialFuel);
            return ResponseEntity.ok("Fueling started for pump ID " + pumpId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<Pump>> getPumps(){
        try {
            List<Pump> pumps = pumpService.getAllPumps();
            return ResponseEntity.ok(pumps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/reservers")
    public ResponseEntity<Map<String, String>> getReservers(){
        try {
            Map<String, String> reservations = pumpService.getReservers();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Map<String, String>) new ArrayList<>());
        }
    }
}
