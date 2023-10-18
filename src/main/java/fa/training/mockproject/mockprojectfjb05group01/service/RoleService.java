package fa.training.mockproject.mockprojectfjb05group01.service;

import fa.training.mockproject.mockprojectfjb05group01.dto.RoleDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Role;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;


import java.util.List;

public interface RoleService {
    Role findByRoleType(RoleType roleName);

    List<Role> findByRole();

    Role convertToEntity(RoleDTO roleData);

    RoleDTO convertToDTO(Role role);

    Role getRoleById(int id);
    int getRoleIdByRoleType(String roleType);

}
