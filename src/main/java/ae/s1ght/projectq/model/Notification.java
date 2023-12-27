package ae.s1ght.projectq.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime creationDate;

    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }
}
