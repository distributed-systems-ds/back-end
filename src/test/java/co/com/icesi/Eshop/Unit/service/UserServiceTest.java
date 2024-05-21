package co.com.icesi.Eshop.Unit.service;

import co.com.icesi.Eshop.Unit.util.CrudTest;
import co.com.icesi.Eshop.Unit.util.Matcher.UserMatcher;
import co.com.icesi.Eshop.dto.request.UserDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import co.com.icesi.Eshop.dto.response.UserResponseDTO;
import co.com.icesi.Eshop.mapper.UserMapper;
//import co.com.icesi.Eshop.;
import co.com.icesi.Eshop.model.Role;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.repository.RoleRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import co.com.icesi.Eshop.service.UserService;
import co.com.icesi.Eshop.service.security.AuthoritiesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultUser;
import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultUserPrincipalDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest implements CrudTest {
    private UserService userService;
    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private AuthoritiesService authoritiesService;
    private UserMapper userMapper;

    PasswordEncoder encoder;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userMapper = spy(UserMapper.class);
        authoritiesService = mock(AuthoritiesService.class);
        encoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository,roleRepository,authoritiesService,encoder,userMapper);
    }


    @Test
    @Override
    public void createTest() {
        // Arrange
        UserDTO userDTO =defaultUserPrincipalDTO();

        UserPrincipal userPrincipal = defaultUser();
        userPrincipal.setEmail(userDTO.getEmail());
        userPrincipal.setPhoneNumber(userDTO.getPhoneNumber());
        userPrincipal.setRole(new Role());
        userPrincipal.setPassword("encodedPassword");

        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setEmail(userDTO.getEmail());
        expectedResponse.setPhoneNumber(userDTO.getPhoneNumber());
        expectedResponse.setRole("USER");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber())).thenReturn(Optional.empty());
        when(userMapper.toUser(userDTO)).thenReturn(userPrincipal);
        when(roleRepository.findByRoleName(userDTO.getRole())).thenReturn(Optional.of(new Role()));
        when(encoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userPrincipal)).thenReturn(userPrincipal);
        when(userMapper.toUserResponseDTO(userPrincipal)).thenReturn(expectedResponse);

        // Act
        UserResponseDTO result = userService.createUser(userDTO);

        // Assert
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userRepository).findByPhoneNumber(userDTO.getPhoneNumber());
        verify(userMapper).toUser(userDTO);
        verify(roleRepository).findByRoleName(userDTO.getRole());
        verify(encoder).encode(userDTO.getPassword());
        verify(userRepository).save(argThat(new UserMatcher(userPrincipal)));
        verify(userMapper).toUserResponseDTO(userPrincipal);
        assertNotNull(result);
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPhoneNumber("123456789");
        userDTO.setRole("ROLE_USER");
        userDTO.setPassword("password");

        UserPrincipal existingUser = new UserPrincipal();
        existingUser.setEmail(userDTO.getEmail());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userRepository, never()).findByPhoneNumber(any());
        verify(userMapper, never()).toUser(any(UserDTO.class));
        verify(roleRepository, never()).findByRoleName(any());
        verify(encoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserResponseDTO(any(UserPrincipal.class));
    }

    @Test
    public void testCreateUser_PhoneAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPhoneNumber("123456789");
        userDTO.setRole("USER");
        userDTO.setPassword("password");

        UserPrincipal existingUser = new UserPrincipal();
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userRepository).findByPhoneNumber(userDTO.getPhoneNumber());
        verify(userMapper, never()).toUser(any(UserDTO.class));
        verify(roleRepository, never()).findByRoleName(any());
        verify(encoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserResponseDTO(any(UserPrincipal.class));
    }

    @Test
    @Override
    public void readTest() {
        // Arrange
        UserPrincipal user1 = defaultUser();

        UserPrincipal user2 = defaultUser();
                List<UserPrincipal> userList = Arrays.asList(user1, user2);

        UserResponseDTO userResponseDTO1 = new UserResponseDTO();
        userResponseDTO1.setEmail(defaultUser().getEmail());
        UserResponseDTO userResponseDTO2 = new UserResponseDTO();
        userResponseDTO2.setPhoneNumber(defaultUser().getPhoneNumber());

        List<UserResponseDTO> expectedResponse = Arrays.asList(userResponseDTO1, userResponseDTO2);

        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toUserResponseDTO(user1)).thenReturn(userResponseDTO1);
        when(userMapper.toUserResponseDTO(user2)).thenReturn(userResponseDTO2);

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toUserResponseDTO(user1);
        verify(userMapper, times(1)).toUserResponseDTO(user2);
        assertEquals(expectedResponse, result);
    }
    @Test
    @Override
    public void updateTest() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setRole("ROLE_USER");

        UserPrincipal userPrincipalInDB = new UserPrincipal();
        userPrincipalInDB.setEmail(userDTO.getEmail());
        userPrincipalInDB.setRole(new Role());

        UserPrincipal userPrincipalUpdated = new UserPrincipal();
        userPrincipalUpdated.setEmail(userDTO.getEmail());
        userPrincipalUpdated.setRole(new Role());

        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setEmail(userDTO.getEmail());
        expectedResponse.setRole("ROLE_USER");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(userPrincipalInDB));
        when(userMapper.toUser(userDTO)).thenReturn(userPrincipalUpdated);
        when(roleRepository.findByRoleName(userDTO.getRole())).thenReturn(Optional.of(new Role()));
        when(userRepository.save(userPrincipalUpdated)).thenReturn(userPrincipalUpdated);
        when(userMapper.toUserResponseDTO(userPrincipalUpdated)).thenReturn(expectedResponse);

        // Act
        UserResponseDTO result = userService.updateUser(userDTO);

        // Assert
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userMapper).toUser(userDTO);
        verify(roleRepository).findByRoleName(userDTO.getRole());
        verify(userRepository,times(2)).save(userPrincipalUpdated);
        verify(userMapper).toUserResponseDTO(userPrincipalUpdated);
        assertEquals(expectedResponse, result);
    }
    @Test
    @Override
    public void deleteTest() {
        // Arrange
        String userEmail = "test@example.com";
        // String userEmailToDelete = userEmail.substring(1, userEmail.length() - 1);

        UserPrincipal userPrincipal = new UserPrincipal();

        userPrincipal.setEmail(userEmail);

        UserResponseDTO expectedResponse = new UserResponseDTO();

        expectedResponse.setEmail(userEmail);

        Optional<UserPrincipal> userOptional = Optional.of(userPrincipal);

        when(userRepository.findByEmail(userEmail)).thenReturn(userOptional);
        when(userMapper.toUserResponseDTO(userPrincipal)).thenReturn(expectedResponse);

        // Act
        UserResponseDTO result = userService.deleteUser(userEmail);

        // Assert
        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(userRepository,times(1)).delete(argThat(new UserMatcher(userPrincipal)));
        verify(userMapper, times(1)).toUserResponseDTO(userPrincipal);
        assertNotNull(result);
    }


    @Test
    public void testUpdateUser_UserNotFound() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(userDTO));
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userMapper, never()).toUser(any(UserDTO.class));
        verify(roleRepository, never()).findByRoleName(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserResponseDTO(any(UserPrincipal.class));
    }

    @Test
    public void testUpdateUser_RoleNotFound() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setRole("ROLE_USER");

        UserPrincipal userPrincipalInDB = new UserPrincipal();
        userPrincipalInDB.setEmail(userDTO.getEmail());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(userPrincipalInDB));
        when(roleRepository.findByRoleName(userDTO.getRole())).thenReturn(Optional.empty());
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(userPrincipalInDB);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(userDTO));
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(roleRepository, times(1)).findByRoleName(userDTO.getRole());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserResponseDTO(any(UserPrincipal.class));
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        // Arrange
        String userEmail = "test@example.com";


        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(userEmail));
        verify(userRepository).findByEmail(userEmail);
        verify(userRepository, never()).delete(any());
        verify(userMapper, never()).toUserResponseDTO(any(UserPrincipal.class));
    }


}
