package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, length = 10)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email" ,length = 254)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "activation_Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean activationStatus;

    @UpdateTimestamp
    private Timestamp updateTime;


    @CreationTimestamp
    private Timestamp createdTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by_id")
    private User updateByUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_id")
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_hotel", joinColumns = {
            @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "hotel_id"))
    private List<Hotel> listHotel = new ArrayList<>();

}
