package fa.training.mockproject.mockprojectfjb05group01.repository;

import fa.training.mockproject.mockprojectfjb05group01.entity.Role;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r.id from Role r  WHERE r.roleType = ?1")
    int findRoleByRoleType(String roleType);

    Role findByRoleType(RoleType roleName);

    @Query("select r.id from Role r where r.roleType = ?1")
    int getRoleIdByRoleType(String roleType);

}
