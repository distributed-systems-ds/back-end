package co.com.icesi.Eshop.repository;

import co.com.icesi.Eshop.model.security.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthoritiesRepository extends JpaRepository<Authorities, UUID> {

    Optional<Authorities> findByAuthority(String authority);
}
