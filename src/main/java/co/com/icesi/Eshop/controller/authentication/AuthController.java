package co.com.icesi.Eshop.controller.authentication;

import co.com.icesi.Eshop.api.AuthorityApi;
import co.com.icesi.Eshop.dto.request.AuthorityDTO;
import co.com.icesi.Eshop.dto.response.AuthorityResponseDTO;
import co.com.icesi.Eshop.dto.security.LoginDTO;
import co.com.icesi.Eshop.dto.security.JwtDTO;
import co.com.icesi.Eshop.service.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class AuthController implements AuthorityApi {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtDTO token(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));
        return tokenService.generateToken(authentication);
    }

    /**
     * @param authorityDTO
     * @return
     */
    @Override
    public AuthorityResponseDTO createAuthority(AuthorityDTO authorityDTO) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<AuthorityResponseDTO> getAllAuthorities() {
        return null;
    }
}
