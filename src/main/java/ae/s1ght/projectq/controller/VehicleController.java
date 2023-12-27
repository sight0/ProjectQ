package ae.s1ght.projectq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/vehicles")
@AllArgsConstructor
public class VehicleController {


//    @PostMapping("/")
//    public ResponseEntity<String> requestAccount(
//            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
//            @RequestParam String accountType
//    ){
//        customerService.requestBankAccount(auth, accountType);
//        return ResponseEntity.ok().body("Submitted your request to open a bank account!");
//    }

//    @GetMapping("/vehicles/")
//    public ResponseEntity<String> requestLoan(
//            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
//            @RequestParam String userID
//    ){
//
//        String response = customerService.requestLoan(auth, accountNumber, amount);
//        return ResponseEntity.ok().body(response);
//    }
}
