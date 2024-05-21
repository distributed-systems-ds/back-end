package co.com.icesi.Eshop.model;

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
public class OrderStore {

    @Id
    private UUID orderId;


    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "user_principal_user_id")
    private UserPrincipal userPrincipal;


    private String status;

    private Long total;


    @ManyToMany()
    @JoinTable(
            name = "item_order_store",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

}
