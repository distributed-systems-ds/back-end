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
public class Item {

    @Id
    private UUID itemId;

    private String description;

    private String name;

    private Long price;

    private String imageUrl;


    @ManyToOne()
    @JoinColumn(name = "category_categoryId")
    private Category category;

    @ManyToMany(mappedBy = "items")
    private List<OrderStore> orderStores;



}
