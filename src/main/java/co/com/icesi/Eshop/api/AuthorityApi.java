package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.AuthorityDTO;
import co.com.icesi.Eshop.dto.response.AuthorityResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(AuthorityApi.BASE_URL)
public interface AuthorityApi {
    String BASE_URL = "/api/authorities";

    @PostMapping("/create")
    AuthorityResponseDTO createAuthority(@RequestBody @Valid AuthorityDTO authorityDTO);

    @GetMapping("/all")
    List<AuthorityResponseDTO> getAllAuthorities();
}
