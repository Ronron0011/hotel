package fa.training.mockproject.mockprojectfjb05group01.service.impl;

import fa.training.mockproject.mockprojectfjb05group01.dto.RoleDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.Role;
import fa.training.mockproject.mockprojectfjb05group01.entity.Room;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;
import fa.training.mockproject.mockprojectfjb05group01.repository.RoleRepository;
import fa.training.mockproject.mockprojectfjb05group01.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleType(RoleType roleName) {
        return roleRepository.findByRoleType(roleName);
    }

    @Override
    public List<Role> findByRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role convertToEntity(RoleDTO roleData) {
        Role role = new Role();
        role.setId(roleData.getId());
        role.setRoleType(roleData.getRoleType());
        role.setDescription(roleData.getDescription());
        return role;
    }

    @Override
    public RoleDTO convertToDTO(Role role) {
        RoleDTO roleData = new RoleDTO();
        roleData.setId(role.getId());
        roleData.setRoleType(role.getRoleType());
        roleData.setDescription(role.getDescription());
        return roleData;
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.getReferenceById((long) id);
    }

    @Override
    public int getRoleIdByRoleType(String roleType) {
        return roleRepository.getRoleIdByRoleType(roleType);
    }




}
