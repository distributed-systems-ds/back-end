package co.com.icesi.Eshop.model;

import co.com.icesi.Eshop.model.security.Authorities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private UUID roleId;

    private String roleName;

    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserPrincipal> userPrincipals;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authorities> authorities;
}
