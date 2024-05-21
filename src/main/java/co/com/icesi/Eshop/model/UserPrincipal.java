package co.com.icesi.Eshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {

    @Id
    private UUID userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private String birthDate;


    @ManyToOne()
    @JoinColumn(name = "role_role_id")
    private Role role;


    @OneToMany(mappedBy = "userPrincipal" , cascade = javax.persistence.CascadeType.ALL)
    private List<OrderStore> orderStores;



}
