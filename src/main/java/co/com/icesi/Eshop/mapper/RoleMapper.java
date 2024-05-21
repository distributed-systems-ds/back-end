package co.com.icesi.Eshop.mapper;

import co.com.icesi.Eshop.dto.request.RoleDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import co.com.icesi.Eshop.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDTO roleDTO);

    Role toRole(RoleResponseDTO role);

    RoleDTO toRoleDTO(Role role);

    RoleDTO toRoleDTO(RoleResponseDTO role);

    RoleResponseDTO toRoleResponseDTO(Role role);

    RoleResponseDTO toRoleResponseDTO(RoleDTO roleDTO);
}
