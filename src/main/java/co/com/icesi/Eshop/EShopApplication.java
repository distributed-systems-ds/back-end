package co.com.icesi.Eshop;

import co.com.icesi.Eshop.model.*;
import co.com.icesi.Eshop.model.security.Authorities;
import co.com.icesi.Eshop.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@SpringBootApplication
public class EShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopApplication.class, args);
	}




	//@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository,
												 RoleRepository roleRepository,
												 OrderRepository orderRepository,
												 ItemRepository itemRepository,
												 CategoryRepository categoryRepository,
												 AuthoritiesRepository authoritiesRepository,
												 PasswordEncoder encoder) {

		Authorities authorities1 = Authorities.builder()
				.authority("/**")
				.build();
		Authorities authorities2 = Authorities.builder()
				.authority("/api/users/**")
				.build();
		Authorities authorities3 = Authorities.builder()
				.authority("/api/role/**")
				.build();
		Authorities authorities4 = Authorities.builder()
				.authority("/api/order/**")
				.build();
		Authorities authorities5 = Authorities.builder()
				.authority("/api/item/**")
				.build();
		////////////////////////////////////////
		Role role1 = Role.builder()
				.roleId(UUID.fromString("a351796c-ff13-11ed-be56-0242ac120002"))
				.roleName("ADMIN")
				.description("Administrator")
				.authorities(new ArrayList<>(Collections.singletonList(authorities1)))
				.build();
		Role role2 = Role.builder()
				.roleId(UUID.fromString("a351796c-ff13-11ed-be56-0242ac120003"))
				.roleName("USER")
				.description("UserPrincipal")
				.authorities(new ArrayList<>(Collections.singletonList(authorities2)))
				.build();
		Role role3 = Role.builder()
				.roleId(UUID.fromString("a351796c-ff13-11ed-be56-0242ac120004"))
				.roleName("Store")
				.description("Store")
				.authorities(new ArrayList<>(Arrays.asList(authorities4,authorities5)))
				.build();
		Role role4 = Role.builder()
				.roleId(UUID.fromString("a351796c-ff13-11ed-be56-0242ac120005"))
				.roleName("Supervisor")
				.description("Supervisor")
				.authorities(new ArrayList<>(Arrays.asList(authorities3,authorities4,authorities5)))
				.build();
		////////////////////////////////////////

		Category category1 = Category.builder()
				.categoryId(UUID.fromString("a3517ba6-ff13-11ed-be56-0242ac120002"))
				.name("Category 1")
				.description("Description 1")
				.build();
		Category category2 = Category.builder()
				.categoryId(UUID.fromString("a3517ba6-ff13-11ed-be56-0242ac120003"))
				.name("Category 2")
				.description("Description 2")
				.build();
		Category category3 = Category.builder()
				.categoryId(UUID.fromString("a3517ba6-ff13-11ed-be56-0242ac120004"))
				.name("Category 3")
				.description("Description 3")
				.build();
		Category category4 = Category.builder()
				.categoryId(UUID.fromString("a3517ba6-ff13-11ed-be56-0242ac120005"))
				.name("Category 4")
				.description("Description 4")
				.build();
		////////////////////////////////////////

		UserPrincipal user1 = UserPrincipal.builder()
				.userId(UUID.fromString("a3516a26-ff13-11ed-be56-0242ac120002"))
				.firstName("John")
				.lastName("Doe")
				.email("email1@email.com")
				.password(encoder.encode("password"))
				.birthDate("1/1/1999")
				.phoneNumber("3123342122")
				.address("Calle 1 # 1-1")
				.role(role1)
				.build();
		UserPrincipal user2 = UserPrincipal.builder()
				.userId(UUID.fromString("a3516a26-ff13-11ed-be56-0242ac120003"))
				.firstName("Jane")
				.lastName("Doe")
				.email("email2@email.com")
				.password(encoder.encode("password"))
				.birthDate("1/1/1999")
				.phoneNumber("3123342122")
				.address("Calle 1 # 1-1")
				.role(role2)
				.build();
		UserPrincipal user3 = UserPrincipal.builder()
				.userId(UUID.fromString("a3516a26-ff13-11ed-be56-0242ac120004"))
				.firstName("Jack")
				.lastName("Doe")
				.email("email3@email.com")
				.password(encoder.encode("password"))
				.birthDate("1/1/1999")
				.phoneNumber("3123342122")
				.address("Calle 1 # 1-1")
				.role(role3)
				.build();
		////////////////////////////////////////

		Item item1 = Item.builder()
				.itemId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120002"))
				.name("Item 1")
				.description("Description 1")
				.imageUrl("https://via.placeholder.com/150")
				.price(1000L)
				.category(category1)
				.build();
		Item item2 = Item.builder()
				.itemId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120003"))
				.name("Item 2")
				.description("Description 2")
				.imageUrl("https://via.placeholder.com/150")
				.price(2000L)
				.category(category2)
				.build();
		Item item3 = Item.builder()
				.itemId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120004"))
				.name("Item 3")
				.description("Description 3")
				.imageUrl("https://via.placeholder.com/150")
				.price(3000L)
				.category(category3)
				.build();
		Item item4 = Item.builder()
				.itemId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120005"))
				.name("Item 4")
				.description("Description 4")
				.imageUrl("https://via.placeholder.com/150")
				.price(4000L)
				.category(category4)
				.build();
		////////////////////////////////////////
		OrderStore orderStore1 = OrderStore.builder()
				.orderId(UUID.fromString("a35184d4-ff13-11ed-be56-0242ac120002"))
				.userPrincipal(user1)
				.items(Arrays.asList(item1, item2))
				.status("PENDING")
				.total(3000L)
				.build();
		OrderStore orderStore2 = OrderStore.builder()
				.orderId(UUID.fromString("a35184d4-ff13-11ed-be56-0242ac120003"))
				.userPrincipal(user2)
				.items(Arrays.asList(item3, item4))
				.status("PENDING")
				.total(3000L)
				.build();
		OrderStore orderStore3 = OrderStore.builder()
				.orderId(UUID.fromString("a35184d4-ff13-11ed-be56-0242ac120004"))
				.userPrincipal(user3)
				.items(Arrays.asList(item1, item2, item3, item4))
				.status("PENDING")
				.total(3000L)
				.build();
		////////////////////////////////////////
		return args -> {
			authoritiesRepository.saveAll(Arrays.asList(authorities1, authorities2));
			roleRepository.saveAll(Arrays.asList(role1, role2, role3, role4));
			categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4));
			userRepository.saveAll(Arrays.asList(user1, user2, user3));
			itemRepository.saveAll(Arrays.asList(item1, item2, item3, item4));
			orderRepository.saveAll(Arrays.asList(orderStore1, orderStore2, orderStore3));
		};


	}

	

}
