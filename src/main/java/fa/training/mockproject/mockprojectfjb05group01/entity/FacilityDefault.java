package fa.training.mockproject.mockprojectfjb05group01.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDefault {
    @Id
    private int id;

    private String name;
}
