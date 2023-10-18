package fa.training.mockproject.mockprojectfjb05group01.entity;

import fa.training.mockproject.mockprojectfjb05group01.entity.enums.HotelStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel_info")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @Column(name = "hotel_name", nullable = false, length = 200)
    private String hotelName;

    @Column(name = "hotel_type", nullable = false, length = 100)
    private String hotelType;

    @Column(name = "hotel_star_level", nullable = false, length = 5)
    private int hotelStarLevel;

    @Column(name = "hotel_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private HotelStatus hotelStatus;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "district" ,nullable = false)
    private  String district;

    @Column(name = "ward",nullable = false)
    private String ward;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @CreationTimestamp
    @Column(name="created_time", nullable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name="updated_time", nullable=false, updatable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Room> listRoom;


     @ManyToMany(mappedBy = "listHotel")
     private List<User> user;

    @OneToMany(mappedBy = "hotel")
    private List<Facility> facilities;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<ServiceHotel> listService;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private  List<Image> listImagesHotel;
}
