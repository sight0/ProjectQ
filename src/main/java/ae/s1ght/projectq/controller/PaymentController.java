package ae.s1ght.projectq.controller;

import ae.s1ght.projectq.enums.PaymentMethod;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.model.*;
import ae.s1ght.projectq.service.impl.LaneServiceIMPL;
import ae.s1ght.projectq.service.impl.PaymentServiceIMPL;
import ae.s1ght.projectq.service.impl.PumpServiceIMPL;
import ae.s1ght.projectq.service.impl.VehicleServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentServiceIMPL paymentServiceIMPL;
    private final PumpServiceIMPL pumpServiceIMPL;
    private final VehicleServiceIMPL vehicleServiceIMPL;
    private final LaneServiceIMPL laneServiceIMPL;

    @PostMapping("/invoice")
    public ResponseEntity<Map<String, Long>> createInvoice(
            @RequestParam Long pumpId,
            @RequestParam PaymentMethod paymentMethod
    ){
        try {
            User user;
            Pump pump = pumpServiceIMPL.getPumpById(pumpId);
            if(pump.getStatus() != PumpStatus.COMPLETE)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            double amountPayable = pumpServiceIMPL.getAmountPayable(pump);
            Payment payment = new Payment(null, amountPayable, paymentMethod);
            payment.setRef(pump.getId().toString());
            paymentServiceIMPL.createPayment(payment);
            Map<String, Long> invoiceId = new HashMap<>();
            invoiceId.put("invoiceID", payment.getId());
            return ResponseEntity.ok(invoiceId);
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
        }
    }

    @PostMapping("/success")
    public ResponseEntity<Map<String, String>> process(
            @RequestParam Long invoiceID
    ){
        try {
            Payment payment = paymentServiceIMPL.getPaymentById(invoiceID);
            Pump pump = pumpServiceIMPL.getPumpById(Long.valueOf(payment.getRef()));
            Vehicle vehicle = pump.getVehicle();
            String plateNumber = pump.getVehicle().getLicensePlate();
            Lane lane = pumpServiceIMPL.resetPump(pump);
            laneServiceIMPL.updateLaneStatus(lane);
            vehicle.setLane(null);
            vehicleServiceIMPL.saveVehicle(vehicle);
            paymentServiceIMPL.processPayment(payment);
            Map<String, String> invoiceId = new HashMap<>();
            invoiceId.put("plateNumber", plateNumber);
            return ResponseEntity.ok(invoiceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
        }
    }
}