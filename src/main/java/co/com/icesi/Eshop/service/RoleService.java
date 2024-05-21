package co.com.icesi.Eshop.service;

import co.com.icesi.Eshop.dto.request.RoleDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import co.com.icesi.Eshop.mapper.RoleMapper;
import co.com.icesi.Eshop.model.Role;
import co.com.icesi.Eshop.repository.RoleRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    public RoleResponseDTO createRole(RoleDTO roleDTO) {
        validateRoleNameDontExist(roleDTO.getRoleName());
        Role role = roleMapper.toRole(roleDTO);
        role.setRoleId(UUID.randomUUID());
        return roleMapper.toRoleResponseDTO(roleRepository.save(role));
    }

    private void validateRoleNameDontExist(String roleName) {
        roleRepository.findByRoleName(roleName).ifPresent(role -> {
            throw new RuntimeException("Role with " + roleName + " already exists");
        });
    }

    public RoleResponseDTO updateRole(RoleDTO roleDTO) {
        Role roleInDB = roleRepository.findByRoleName(roleDTO.getRoleName()).orElseThrow(() -> new RuntimeException("Role with " + roleDTO.getRoleName() + " does not exists"));
        roleInDB.setDescription(roleDTO.getDescription());
        roleInDB.setRoleName(roleDTO.getRoleName());
        return roleMapper.toRoleResponseDTO(roleRepository.save(roleInDB));
    }

    public RoleResponseDTO deleteRole(String roleName) {
        String roleNameTest = roleName;
        if(roleName.matches(".*\".*")){
             roleNameTest = roleName.substring(1, roleName.length() - 1);
        }

        Role role = roleRepository.findByRoleName(roleNameTest).orElseThrow(() -> new RuntimeException("Role with " + roleName + " does not exists"));
        if(userRepository.findAll().stream().filter(user -> user.getRole().getRoleId().equals(role.getRoleId())).toList().size()>0) throw new RuntimeException("Role with " + roleName + " is in use");
        roleRepository.delete(role);
        return roleMapper.toRoleResponseDTO(role);
    }

    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponseDTO).toList();
    }
}
