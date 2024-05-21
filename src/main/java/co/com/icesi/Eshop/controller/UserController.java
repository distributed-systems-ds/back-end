package co.com.icesi.Eshop.controller;

import co.com.icesi.Eshop.api.UserApi;
import co.com.icesi.Eshop.dto.request.UserDTO;
import co.com.icesi.Eshop.dto.response.UserResponseDTO;
import co.com.icesi.Eshop.mapper.UserMapper;
import co.com.icesi.Eshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * @param userDTO userDTO
     * @return UserResponseDTO userDTO
     */
    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    /**
     * @param userDTO userDTO
     * @return UserResponseDTO userDTO
     */
    @Override
    public UserResponseDTO updateUser(UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    /**
     * @param email userDTO
     * @return UserResponseDTO userDTO
     */
    @Override
    public UserResponseDTO deleteUser(String email) {
        return userService.deleteUser(email);
    }

    /**
     * @return List<UserResponseDTO> userPrincipals
     */
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
