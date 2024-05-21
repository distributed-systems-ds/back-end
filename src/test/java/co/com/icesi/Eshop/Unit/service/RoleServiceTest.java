package co.com.icesi.Eshop.Unit.service;

import co.com.icesi.Eshop.Unit.util.CrudTest;
import co.com.icesi.Eshop.Unit.util.Matcher.RoleMatcher;
import co.com.icesi.Eshop.dto.request.RoleDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import co.com.icesi.Eshop.mapper.RoleMapper;
import co.com.icesi.Eshop.mapper.RoleMapperImpl;
import co.com.icesi.Eshop.mapper.UserMapper;
import co.com.icesi.Eshop.model.Role;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.repository.RoleRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import co.com.icesi.Eshop.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultRole;
import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultRoleDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RoleServiceTest  implements CrudTest {
    private  RoleRepository roleRepository;
    private RoleMapper roleMapper;

    private UserRepository userRepository;

    private RoleService roleService;

    @BeforeEach
    public void init(){
        roleMapper = spy(RoleMapperImpl.class);
        roleRepository = mock(RoleRepository.class);
        userRepository = mock(UserRepository.class);
        roleService = new RoleService(roleRepository,userRepository,roleMapper);
    }


    @Test
    @Override
    public void createTest() {
        // Arrange
        RoleDTO roleDTO = defaultRoleDTO();

        Role role = defaultRole();
        role.setRoleName(roleDTO.getRoleName());

        RoleResponseDTO expectedResponse = new RoleResponseDTO();
        expectedResponse.setRoleName(roleDTO.getRoleName());

        when(roleRepository.findByRoleName(roleDTO.getRoleName())).thenReturn(Optional.empty());
        when(roleMapper.toRole(roleDTO)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toRoleResponseDTO(role)).thenReturn(expectedResponse);

        // Act
        roleService.createRole(roleDTO);

        // Assert
        verify(roleRepository).findByRoleName(roleDTO.getRoleName());
        verify(roleMapper).toRole(roleDTO);
        verify(roleRepository).save(argThat(new RoleMatcher(role)));
        verify(roleMapper).toRoleResponseDTO(role);

    }

    @Test
    public void testCreateRole_RoleNameAlreadyExists() {
        // Arrange
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("RoleName");

        Role existingRole = new Role();
        existingRole.setRoleName(roleDTO.getRoleName());

        when(roleRepository.findByRoleName(roleDTO.getRoleName())).thenReturn(Optional.of(existingRole));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.createRole(roleDTO));
        verify(roleRepository).findByRoleName(roleDTO.getRoleName());
        verify(roleMapper, never()).toRole(any(RoleDTO.class));
        verify(roleRepository, never()).save(any());
        verify(roleMapper, never()).toRoleResponseDTO(any(Role.class));
    }

    @Test
    @Override
    public void readTest() {
        // Arrange
        Role role1 = new Role();
        role1.setRoleName("Role1");

        Role role2 = new Role();
        role2.setRoleName("Role2");

        List<Role> roles = Arrays.asList(role1, role2);

        RoleResponseDTO roleResponse1 = new RoleResponseDTO();
        roleResponse1.setRoleName("Role1");

        RoleResponseDTO roleResponse2 = new RoleResponseDTO();
        roleResponse2.setRoleName("Role2");

        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toRoleResponseDTO(role1)).thenReturn(roleResponse1);
        when(roleMapper.toRoleResponseDTO(role2)).thenReturn(roleResponse2);

        // Act
        List<RoleResponseDTO> result = roleService.getAllRoles();

        // Assert
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toRoleResponseDTO(role1);
        verify(roleMapper, times(1)).toRoleResponseDTO(role2);
        assertEquals(2, result.size());
        assertEquals(roleResponse1, result.get(0));
        assertEquals(roleResponse2, result.get(1));
    }

    @Test
    @Override
    public void updateTest() {
        // Arrange
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("RoleName");
        roleDTO.setDescription("Updated description");

        Role roleInDB = new Role();
        roleInDB.setRoleName("RoleName");
        roleInDB.setDescription("Old description");

        Role updatedRole = new Role();
        updatedRole.setRoleName("RoleName");
        updatedRole.setDescription("Updated description");

        RoleResponseDTO expectedResponse = new RoleResponseDTO();

        expectedResponse.setRoleName("RoleName");
        expectedResponse.setDescription("Updated description");

        when(roleRepository.findByRoleName(roleDTO.getRoleName())).thenReturn(Optional.of(roleInDB));
        when(roleRepository.save(roleInDB)).thenReturn(updatedRole);
        when(roleMapper.toRoleResponseDTO(updatedRole)).thenReturn(expectedResponse);

        // Act
        RoleResponseDTO result = roleService.updateRole(roleDTO);

        // Assert
        verify(roleRepository, times(1)).findByRoleName(roleDTO.getRoleName());
        verify(roleRepository, times(1)).save(argThat(new RoleMatcher(roleInDB)));
        verify(roleMapper, times(1)).toRoleResponseDTO(updatedRole);
        assertEquals(expectedResponse, result);

    }

    @Test
    public void testUpdateRole_RoleNotFound() {
        // Arrange
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("Non-existent Role");
        roleDTO.setDescription("Updated description");

        when(roleRepository.findByRoleName(roleDTO.getRoleName())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.updateRole(roleDTO));
        verify(roleRepository).findByRoleName(roleDTO.getRoleName());
        verify(roleRepository, never()).save(any());
        verify(roleMapper, never()).toRoleResponseDTO(any(Role.class));
    }

    @Test
    @Override
    public void deleteTest() {
        // Arrange
        String roleName = "RoleName";

        Role role = defaultRole();
        role.setRoleName(roleName);

        RoleResponseDTO expectedResponse = new RoleResponseDTO();
        expectedResponse.setRoleName(roleName);
        expectedResponse.setDescription(defaultRole().getDescription());

        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(role));
        when(roleMapper.toRoleResponseDTO(role)).thenReturn(expectedResponse);
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        RoleResponseDTO result = roleService.deleteRole(roleName);

        // Assert
        verify(roleRepository,times(1)).findByRoleName(roleName);
        verify(roleRepository, times(1)).delete(role);
        verify(roleMapper, times(1)).toRoleResponseDTO(role);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testDeleteRole_RoleNotFound() {
        // Arrange
        String roleName = "Non-existent Role";

        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.deleteRole(roleName));
        verify(roleRepository).findByRoleName(roleName);
        verify(roleRepository, never()).delete(any());
        verify(roleMapper, never()).toRoleResponseDTO(any(Role.class));
    }

    @Test
    public void testDeleteRole_RoleInUse() {
        // Arrange
        String roleName = "RoleName";

        Role role = new Role();
        role.setRoleName(roleName);

        UserPrincipal user = new UserPrincipal();
        user.setRole(role);

        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(role));
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.deleteRole(roleName));
        verify(roleRepository).findByRoleName(roleName);
        verify(roleRepository, never()).delete(any());
        verify(roleMapper, never()).toRoleResponseDTO(any(Role.class));
    }
}
