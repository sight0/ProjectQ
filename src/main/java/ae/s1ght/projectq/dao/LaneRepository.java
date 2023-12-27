package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.model.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Long> {
    @Query("SELECT l FROM Lane l JOIN l.vehicleQueue v WHERE v.licensePlate = :licensePlate")
    Optional<Lane> findLaneByVehicleLicensePlate(@Param("licensePlate") String licensePlate);
}
