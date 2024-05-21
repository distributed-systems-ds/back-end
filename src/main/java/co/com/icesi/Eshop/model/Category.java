package co.com.icesi.Eshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private UUID categoryId;

    private String name;

    private String description;


    @OneToMany(mappedBy = "category")
    private List<Item> items;

}
