package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "used_service")
public class UsedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "used_service_id")
    private Long usedServiceId;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(9,2)")
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_amount", nullable = false)
    private float totalAmount;

    @ManyToOne
    @JoinColumn(name = "booked_room_id")
    private BookedRoom bookedRoom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private ServiceHotel serviceHotel;
}
