package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Add custom query methods if needed
}

