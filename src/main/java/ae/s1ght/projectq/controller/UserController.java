package ae.s1ght.projectq.controller;

import ae.s1ght.projectq.enums.CameraLocation;
import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.service.impl.LaneServiceIMPL;
import ae.s1ght.projectq.service.impl.PumpServiceIMPL;
import ae.s1ght.projectq.service.impl.UserServiceIMPL;
import ae.s1ght.projectq.model.User;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class UserController {

    private final UserServiceIMPL userServiceImpl;
    private final LaneServiceIMPL laneServiceIMPL;
    private final PumpServiceIMPL pumpServiceIMPL;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @RequestParam String username,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password
    ){
        User user = new User(username, email, password);
        user.setFirstName(firstname);
        user.setLastName(lastname);

        User createdUser = userServiceImpl.registerUser(user);

        if(createdUser != null) return ResponseEntity.status(HttpStatus.CREATED)
                .body("Successfully created user!");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid input!");
    }

    @PostMapping("/staff/layout")
    public ResponseEntity<String> vehicleDetected(
            @RequestParam int lanes,
            @RequestParam int pumps
    ){
        Lane lane1 = new Lane();
        Lane lane2 = new Lane();

        Pump pump1 = new Pump(lane1);
        Pump pump2 = new Pump(lane1);
        Pump pump3 = new Pump(lane2);
        Pump pump4 = new Pump(lane2);

        laneServiceIMPL.saveLane(lane1);
        laneServiceIMPL.saveLane(lane2);

        pumpServiceIMPL.savePump(pump1);
        pumpServiceIMPL.savePump(pump2);
        pumpServiceIMPL.savePump(pump3);
        pumpServiceIMPL.savePump(pump4);

        laneServiceIMPL.saveLane(lane1);
        laneServiceIMPL.saveLane(lane2);

        return ResponseEntity.ok().build();
    }


}
