package ae.s1ght.projectq;

import ae.s1ght.projectq.dao.PumpRepository;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MockPump {
    private PumpRepository pumpRepository;
    @Scheduled(fixedRate = 2000) // Every 2 seconds
    public void dispenseFuel() {
        List<Pump> dispensingPumps = pumpRepository.findByStatus(PumpStatus.DISPENSING);
        for (Pump pump : dispensingPumps) {
            Vehicle vehicle = pump.getVehicle();
            if (vehicle != null && pump.getFuelDispensed() < vehicle.getFuelCapacity()) {
                double newDispensedAmount = pump.getFuelDispensed() + 10; // Dispense 5 units
                if (newDispensedAmount > vehicle.getFuelCapacity()) {
                    newDispensedAmount = vehicle.getFuelCapacity();
                }
                pump.setFuelDispensed(newDispensedAmount);

                if (newDispensedAmount >= vehicle.getFuelCapacity()) {
                    pump.setStatus(PumpStatus.COMPLETE); // Stop dispensing if capacity reached
                }

                pumpRepository.save(pump);
            }
        }
    }
}
