package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.enums.PumpStatus;
import ae.s1ght.projectq.model.Pump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PumpRepository extends JpaRepository<Pump, Long> {
    // Add custom query methods if needed
    List<Pump> findByStatus(PumpStatus status);
    Optional<Pump> findByVehicleLicensePlate(String licensePlate);
    List<Pump> findAllByStatus(PumpStatus status);
}
