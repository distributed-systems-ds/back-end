package co.com.icesi.Eshop.model.security;

import javax.persistence.*;

import co.com.icesi.Eshop.model.Role;
import co.com.icesi.Eshop.model.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authorities {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    private UUID authorityId;

    private String authority;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Role> roles;
}
