package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "image_info")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Column(name="image_original_name")
    private String imageOriginalName;

    @Column(name = "image_firebase_name")
    private String imageFirebaseName;

    @Column(name = "image_firebase_url")
    private String imageFirebaseUrl;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceHotel service;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image that = (Image) o;
        return Objects.equals(id, that.id) && Objects.equals(imageFirebaseName, that.imageFirebaseName) && Objects.equals(imageFirebaseUrl, that.imageFirebaseUrl) && Objects.equals(hotel, that.hotel) && Objects.equals(room, that.room) && Objects.equals(service, that.service) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageFirebaseName, imageFirebaseUrl, hotel, room, service, user);
    }
}
