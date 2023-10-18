package fa.training.mockproject.mockprojectfjb05group01.entity;

import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "role_info")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "description", length = 300)
    private String description;

    @OneToMany
    private Set<User> userSet;


}
