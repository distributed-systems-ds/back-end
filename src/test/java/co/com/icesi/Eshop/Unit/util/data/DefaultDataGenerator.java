package co.com.icesi.Eshop.Unit.util.data;

import co.com.icesi.Eshop.dto.request.*;
import co.com.icesi.Eshop.model.*;
import co.com.icesi.Eshop.model.security.Authorities;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultDataGenerator {

    public static OrderStore defaultOrder() {
        return OrderStore.builder()
                .userPrincipal(defaultUser())
                .items(Collections.singletonList(defaultItem()))
                .status("PENDING")
                .total(3000L)
                .build();
    }

    public static Item defaultItem() {
        return Item.builder()
                .itemId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120002"))
                .name("Item 1")
                .description("Description 1")
                .imageUrl("https://via.placeholder.com/150")
                .price(1000L)
                .category(defaultCategory())
                .build();
    }

    public static UserPrincipal defaultUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return UserPrincipal.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("email2@email.com")
                .password(encoder.encode("password"))
                .birthDate("1/1/1999")
                .phoneNumber("3123342122")
                .address("Calle 1 # 1-1")
                .role(defaultRole())
                .build();
    }

    public static Category defaultCategory() {
        return Category.builder()
                .categoryId(UUID.fromString("a3517ba6-ff13-11ed-be56-0242ac120005"))
                .name("Category 1")
                .description("Description 1")
                .build();
    }

    public static Role defaultRole() {
        return Role.builder()
                .roleName("Supervisor")
                .description("Supervisor")
                .authorities(new ArrayList<>(Collections.singletonList(defaultAuthority())))
                .build();
    }

    public static Authorities defaultAuthority() {
        return Authorities.builder()
                .authority("ADMIN")
                .build();
    }

    public static CategoryDTO defaultCategoryDTO(){
        return CategoryDTO.builder()
                .name("Category test")
                .description("Description for category test")
                .build();
    }

    public static ItemDTO defaulItemDTO(){
        return ItemDTO.builder()
                .name("Item test")
                .description("Description test")
                .imageUrl("https://via.placeholder.com/150")
                .price(1000L)
                .category("Category test").build();
    }

    public static RoleDTO defaultRoleDTO(){
        return RoleDTO.builder()
                .roleName("ADMIN")
                .description("Administrator")
                .build();
    }

    public static UserDTO defaultUserPrincipalDTO(){
        return UserDTO.builder()
                .email("emailtest@email.com")
                .password("password")
                .phoneNumber("3123842122")
                .firstName("Campaz")
                .lastName("Sasuke")
                .role("USER")
                .build();
    }

    public static OrderDTO defaultOrderDTO(){
        return OrderDTO.builder()
                .userEmail("email2@email.com")
                .status("PENDING")
                .total(100L)
                .items(List.of("Item 1","Item 2"))
                .build();
    }
}
