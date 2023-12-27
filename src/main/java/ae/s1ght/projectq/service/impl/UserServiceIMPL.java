package ae.s1ght.projectq.service.impl;

import ae.s1ght.projectq.model.Notification;
import ae.s1ght.projectq.model.Vehicle;
import ae.s1ght.projectq.service.UserServiceINT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.dao.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceIMPL implements UserDetailsService, UserServiceINT {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("[ERROR] Username '%s' not found!",username)));
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(user.getUserRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authorities));
    }

    @Override
    public User getUser(String username) {
        return userRepository.getUserByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        try {
            User checkUser = getUser(user.getUsername());
            boolean usernameUsed = Objects.equals(checkUser.getUsername(), user.getUsername());
            if(usernameUsed) return null;
        }catch(NullPointerException ignored){}
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole("DRIVER");
        userRepository.save(user);
        //sendVerificationEmail(user.getEmail());
        return user;
    }

    @Override
    public void deleteUser(String username) {
        User user = getUser(username);
        if(user == null) return;// "No user exists with the given username!";
        userRepository.deleteById(user.getId());
        //return "Successfully deleted user!";
    }

    @Override
    public User setPassword(String username, String password) {
        User user = getUser(username);
        if(user == null) return null;
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public User setPin(String username, String pin) {
        User user = getUser(username);
        if(user == null || !pin.matches("^[0-9]{4}$")) return null;// "No user exists with the given username!";
        user.setPIN(pin);
        userRepository.save(user);
        return user;
    }

    @Override
    public User setRole(String username, String role) {
        User user = getUser(username);
        if(user == null) return null;// "No user exists with the given username!";
        user.setUserRole(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public User setEnabled(String username, Boolean enabled) {
        User user = getUser(username);
        if(user == null) return null;
        boolean previous = user.getEnabled();
        user.setEnabled(enabled);
        userRepository.save(user);
        return user;
    }

    @Override
    public User setDOB(String username, LocalDate DOB) {
        User user = getUser(username);
        if(user == null) return null;
        user.setDOB(DOB);
        userRepository.save(user);
        return user;
    }


    @Override
    public String getProfile(String username) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        return user.toString();
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return null;
    }
}
