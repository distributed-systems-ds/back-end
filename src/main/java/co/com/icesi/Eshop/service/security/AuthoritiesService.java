package co.com.icesi.Eshop.service.security;

import co.com.icesi.Eshop.dto.request.AuthorityDTO;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.model.security.Authorities;
import co.com.icesi.Eshop.repository.AuthoritiesRepository;
import co.com.icesi.Eshop.repository.RoleRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import co.com.icesi.Eshop.security.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthoritiesService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public void validateRole(String roleName){
        Optional<UserPrincipal> userPrincipal = userRepository.findById(UUID.fromString(SecurityContext.getCurrentUserId()));
        userPrincipal.ifPresent(actual -> {
            if(!actual.getRole().getRoleName().equals(roleName)){
                throw new RuntimeException("You don't have the authority to do this");
            }
        });
    }

    public void addAuthorityToRole(AuthorityDTO authorityDTO){
        roleRepository.findByRoleName(authorityDTO.getRole()).ifPresent(role -> {

            authoritiesRepository.findByAuthority(authorityDTO.getAuthority()).ifPresent(authorities -> {
               role.getAuthorities().add(authorities);
                roleRepository.save(role);
               return;
            });

            Authorities authorities = Authorities.builder().authority(authorityDTO.getAuthority()).roles(List.of(role)).build();
            role.getAuthorities().add(authorities);
            authoritiesRepository.save(authorities);
            roleRepository.save(role);
        });
    }
}
