package co.com.icesi.Eshop.repository;

import co.com.icesi.Eshop.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserPrincipal, UUID> {

    Optional<UserPrincipal> findByEmail(String email);

    Optional<UserPrincipal> findByPhoneNumber(String phoneNumber);
}
