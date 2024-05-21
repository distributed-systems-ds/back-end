package co.com.icesi.Eshop.controller;

import co.com.icesi.Eshop.api.RoleApi;
import co.com.icesi.Eshop.dto.request.RoleDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import co.com.icesi.Eshop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleController implements RoleApi {

    private final RoleService roleService;
    /**
     * @param roleDTO
     * @return
     */
    @Override
    public RoleResponseDTO createRole(RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    /**
     * @param roleDTO
     * @return
     */
    @Override
    public RoleResponseDTO updateRole(RoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    /**
     * @param roleName
     * @return
     */
    @Override
    public RoleResponseDTO deleteRole(String  roleName) {
        return roleService.deleteRole(roleName);
    }

    /**
     * @return
     */
    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return roleService.getAllRoles();
    }
}
