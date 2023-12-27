package ae.s1ght.projectq.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_manufacturer")
    private String vehicleManufacturer;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "vehicle_year")
    private String vehicleYear;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "fuel_capacity")
    private double fuelCapacity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lane_id")
    private Lane lane;

    @OneToOne(mappedBy = "vehicle")
    private Pump assignedPump;

    private LocalDateTime creationDate;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
        this.creationDate = LocalDateTime.now();
    }

    public Vehicle(String licensePlate, String vehicleType, String vehicleManufacturer, String vehicleModel, String vehicleYear, String fuelType, double fuelCapacity) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.vehicleManufacturer = vehicleManufacturer;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.fuelType = fuelType;
        this.fuelCapacity = fuelCapacity;
        this.creationDate = LocalDateTime.now();
    }
}
