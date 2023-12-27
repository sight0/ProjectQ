package ae.s1ght.projectq.model;

import ae.s1ght.projectq.enums.LaneStatus;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "lanes")
public class Lane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LaneStatus status;

    @Column(name = "accumulated_delay")
    private double accumulatedDelay;


    @OneToMany(mappedBy = "lane")
    private List<Pump> pumps;

    @OneToMany(mappedBy = "lane")
    private List<Vehicle> vehicleQueue;

    public Lane() {
        this.accumulatedDelay = 0.0;
        this.status = LaneStatus.IDLE;
    }
}
