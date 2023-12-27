package ae.s1ght.projectq.model;

import ae.s1ght.projectq.enums.PumpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "pumps")
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lane_id")
    @JsonIgnore
    private Lane lane;

    @Enumerated(EnumType.STRING)
    private PumpStatus status;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;


    @Column(name = "approximate_TTC")
    private double approximateTimeToCompletion;

    @Column(name = "fuel_dispensed")
    private double fuelDispensed;

    @Column(name = "fuel_initial")
    private double fuelInitial;


    public Pump(Lane lane) {
        this.status = PumpStatus.IDLE;
        this.lane = lane;
    }
}
