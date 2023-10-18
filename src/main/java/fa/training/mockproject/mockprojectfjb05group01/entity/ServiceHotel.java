package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_hotel")
public class ServiceHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "name",nullable = false, length = 200)
    private String serviceName;

    @Column(name = "unity", nullable = false, length = 100)
    private String unity;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
