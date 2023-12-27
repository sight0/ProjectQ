package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Add custom query methods if needed
}
