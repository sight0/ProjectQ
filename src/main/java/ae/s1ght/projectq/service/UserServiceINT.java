package ae.s1ght.projectq.service;

import ae.s1ght.projectq.model.Notification;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface UserServiceINT {
    User getUser(String username);
    User registerUser(User user);
    void deleteUser(String username);
    User setPassword(String username, String password);
    User setPin(String username, String pin);
    User setRole(String username, String role);
    User setEnabled(String username, Boolean enabled);
    User setDOB(String username, LocalDate DOB);
    String getProfile(String username);
    List<Vehicle> getVehiclesByUserId(Long userId);
    List<Notification> getNotificationsByUserId(Long userId);

    //  void pushNotification(Notification notification);
    //  void popNotification(Notification notification);
}
