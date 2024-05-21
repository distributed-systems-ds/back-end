package co.com.icesi.Eshop.service;

import co.com.icesi.Eshop.dto.request.UserDTO;
import co.com.icesi.Eshop.dto.response.UserResponseDTO;
import co.com.icesi.Eshop.mapper.UserMapper;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.repository.RoleRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import co.com.icesi.Eshop.service.security.AuthoritiesService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthoritiesService authoritiesService;

    private final PasswordEncoder encoder;
    private final UserMapper userMapper;


    public UserResponseDTO createUser(UserDTO userDTO) {
        userAlreadyExists(userDTO.getEmail());
        phoneAlreadyExists(userDTO.getPhoneNumber());

        UserPrincipal userPrincipal = userMapper.toUser(userDTO);
        if(userDTO.getRole().equals("ADMIN")){authoritiesService.validateRole("ADMIN");}

        userPrincipal.setRole(roleRepository.findByRoleName(userDTO.getRole()).orElseThrow(() -> new RuntimeException("Role with " + userDTO.getRole() + " does not exists")));
        userPrincipal.setUserId(UUID.randomUUID());
        userPrincipal.setPassword(encoder.encode(userDTO.getPassword()));
        return userMapper.toUserResponseDTO(userRepository.save(userPrincipal));
    }

    private void userAlreadyExists(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("UserPrincipal with " + email + " already exists");
        });
    }

    private void phoneAlreadyExists(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber).ifPresent(user -> {
            throw new RuntimeException("UserPrincipal with " + phoneNumber + " already exists");
        });
    }

    public UserResponseDTO updateUser(UserDTO userDTO) {
        UserPrincipal userPrincipalInDB = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new RuntimeException("UserPrincipal with " + userDTO.getEmail() + " does not exists"));
        UserPrincipal userPrincipalUpdated = userMapper.toUser(userDTO);

        userPrincipalUpdated.setUserId(userPrincipalInDB.getUserId());
        userPrincipalUpdated.setRole(roleRepository.findByRoleName(userDTO.getRole()).orElseThrow(() -> new RuntimeException("Role with " + userDTO.getRole() + " does not exists")));
        userPrincipalUpdated.setPassword(userPrincipalInDB.getPassword());

        userRepository.save(userPrincipalUpdated);

        return userMapper.toUserResponseDTO(userRepository.save(userPrincipalUpdated));
    }

    public UserResponseDTO deleteUser(String userEmail) {
        String userEmailToDelete = userEmail;
        if(userEmailToDelete.matches(".*\".*")){
            userEmailToDelete = userEmail.substring(1, userEmail.length() - 1);
        }
        Optional<UserPrincipal> user = Optional.ofNullable(userRepository.findByEmail(userEmailToDelete).orElseThrow(() -> new RuntimeException("UserPrincipal with " + userEmail + " does not exists")));
        return user.map(value -> {
            userRepository.delete(value);
            return userMapper.toUserResponseDTO(value);
        }).orElse(null);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDTO).collect(Collectors.toList());
    }
}
