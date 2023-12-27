package ae.s1ght.projectq.dao;
import ae.s1ght.projectq.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Add custom query methods if needed
}
