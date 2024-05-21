package co.com.icesi.Eshop.mapper;

import co.com.icesi.Eshop.dto.request.UserDTO;
import co.com.icesi.Eshop.dto.response.UserResponseDTO;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    UserPrincipal toUser(UserDTO userDTO);

    @Mapping(target = "role", ignore = true)
    UserPrincipal toUser(UserResponseDTO userResponseDTO);

    @Mapping(target = "role", expression = "java(userPrincipal.getRole().getRoleName())")
    UserDTO toUserDTO(UserPrincipal userPrincipal);

    @Mapping(target = "role", expression = "java(userResponseDTO.getRole())")
    UserDTO toUserDTO(UserResponseDTO userResponseDTO);

    @Mapping(target = "role", expression = "java(userPrincipal.getRole().getRoleName())")
    UserResponseDTO toUserResponseDTO(UserPrincipal userPrincipal);

    UserResponseDTO toUserResponseDTO(UserDTO userDTO);
}
