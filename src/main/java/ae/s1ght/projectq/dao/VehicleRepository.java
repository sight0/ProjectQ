package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> getVehicleByLicensePlate(String plate);

}
