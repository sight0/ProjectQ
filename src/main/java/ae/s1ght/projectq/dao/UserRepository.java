package ae.s1ght.projectq.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.model.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUsername(String username);
    List<User> findByUserRole(String userRole);

}
