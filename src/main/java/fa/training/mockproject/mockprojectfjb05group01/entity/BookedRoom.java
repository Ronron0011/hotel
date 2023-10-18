package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booked_room_info")
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in", nullable = false)
    private Date checkIn;

    @Column(name = "check_out", nullable = false)
    private Date checkOut;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "note")
    private String note;

    @Column(name = "is_checked_in", columnDefinition = "boolean default true")
    private Boolean isCheckedIn;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
