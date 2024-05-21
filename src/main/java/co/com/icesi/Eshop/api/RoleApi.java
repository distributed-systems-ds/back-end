package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.RoleDTO;
import co.com.icesi.Eshop.dto.response.RoleResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(RoleApi.BASE_URL)
public interface RoleApi {

    String BASE_URL = "/api/roles";

    @PostMapping("/create")
    RoleResponseDTO createRole(@RequestBody  @Valid RoleDTO roleResponseDTO);

    @PutMapping("/update")
    RoleResponseDTO updateRole(@RequestBody  @Valid RoleDTO roleResponseDTO);

    @DeleteMapping("/delete")
    RoleResponseDTO deleteRole(@RequestBody String roleName);

    @GetMapping("/all")
    List<RoleResponseDTO> getAllRoles();
}
