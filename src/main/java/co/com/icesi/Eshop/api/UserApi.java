package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.UserDTO;
import co.com.icesi.Eshop.dto.response.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(UserApi.BASE_URL)
public interface UserApi {
    String BASE_URL = "/api/users";

    @PostMapping("/create")
    UserResponseDTO createUser(@RequestBody @Valid UserDTO userDTO);

    @PutMapping("/update")
    UserResponseDTO updateUser(@RequestBody @Valid UserDTO userDTO);

    @DeleteMapping("/delete")
    UserResponseDTO deleteUser(@RequestBody String email);

    @GetMapping("/all")
    List<UserResponseDTO> getAllUsers();
}
