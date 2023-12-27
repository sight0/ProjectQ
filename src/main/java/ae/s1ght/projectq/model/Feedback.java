package ae.s1ght.projectq.model;

import lombok.*;

import ae.s1ght.projectq.enums.FeedbackType;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime creationDate;

    public Feedback(FeedbackType feedbackType, User user, String content) {
        this.feedbackType = feedbackType;
        this.user = user;
        this.content = content;
        this.creationDate = LocalDateTime.now();
    }
}
