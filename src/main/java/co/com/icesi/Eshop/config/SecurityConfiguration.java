package co.com.icesi.Eshop.config;

import co.com.icesi.Eshop.repository.AuthoritiesRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@DependsOn("commandLineRunner")
@ComponentScan(basePackages = "co.com.icesi.Eshop")
@Transactional(propagation = Propagation.REQUIRED)
public class SecurityConfiguration {

    private final AuthenticatorManager icesiAuthenticatorManager;

    private final AuthoritiesRepository authoritiesRepository;
    private final String secret = "longenoughsecrettotestjwtencrypt";

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(icesiAuthenticatorManager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthorizationManager<RequestAuthorizationContext> access) throws Exception {
        return http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().access(access))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = secret.getBytes();
        SecretKeySpec key = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secret.getBytes()));
    }

    @Bean
   @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class, timeout = 1000)
    public AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthorizationManager
            (HandlerMappingIntrospector introspector) {

        Map<RequestMatcher, List<String>> roleMap = authoritiesRepository.findAll().stream()
                .collect(
                        Collectors.toMap(authorities -> new MvcRequestMatcher(introspector, authorities.getAuthority()),
                                permission -> permission.getRoles().stream().map(role -> "SCOPE_" + role.getRoleName()).toList(),
                                (actualList, newList) -> Stream.of(actualList, newList).flatMap(Collection::stream).distinct().toList()
                        )
                );

        RequestMatcher permitAll = new AndRequestMatcher(new MvcRequestMatcher(introspector, "/api/authorities/login"));
        RequestMatcher createUsers = new AndRequestMatcher(new MvcRequestMatcher(introspector, "/api/users/create"));

        RequestMatcherDelegatingAuthorizationManager.Builder managerBuilder = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(permitAll, (context, other) -> new AuthorizationDecision(true));
        managerBuilder.add(createUsers, (context, other) -> new AuthorizationDecision(true));

        roleMap.forEach((matcher, authorities) -> managerBuilder.add(matcher, AuthorityAuthorizationManager.hasAnyAuthority(authorities.toArray(new String[0]))));

        AuthorizationManager<HttpServletRequest> manager = managerBuilder.build();
        return (authentication, object) -> manager.check(authentication, object.getRequest());

    }
}
