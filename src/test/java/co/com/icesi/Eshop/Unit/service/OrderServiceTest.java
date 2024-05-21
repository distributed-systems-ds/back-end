package co.com.icesi.Eshop.Unit.service;

import co.com.icesi.Eshop.Unit.util.CrudTest;
import co.com.icesi.Eshop.Unit.util.Matcher.OrderMatcher;
import co.com.icesi.Eshop.dto.request.OrderDTO;
import co.com.icesi.Eshop.dto.response.OrderResponseDTO;
import co.com.icesi.Eshop.mapper.OrderMapper;
import co.com.icesi.Eshop.mapper.OrderMapperImpl;
import co.com.icesi.Eshop.model.Item;
import co.com.icesi.Eshop.model.OrderStore;
import co.com.icesi.Eshop.model.UserPrincipal;
import co.com.icesi.Eshop.repository.ItemRepository;
import co.com.icesi.Eshop.repository.OrderRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import co.com.icesi.Eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest implements CrudTest {
    private OrderService orderService;
    private  OrderRepository orderRepository;
    private  OrderMapper orderMapper;
    private  ItemRepository itemRepository;
    private  UserRepository userRepository;

    @BeforeEach
    public void init(){
        orderRepository = mock(OrderRepository.class);
        orderMapper = spy(OrderMapperImpl.class);
        itemRepository = mock(ItemRepository.class);
        userRepository = mock(UserRepository.class);
        orderService = new OrderService(orderRepository,orderMapper,itemRepository,userRepository);
    }

    @Test
    @Override
    public void createTest() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail("test@example.com");
        orderDTO.setItems(Collections.singletonList("item1"));

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setEmail(orderDTO.getUserEmail());

        Item item1 = new Item();
        item1.setName("item1");

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.randomUUID());

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setId(String.valueOf(order.getOrderId()));

        when(userRepository.findByEmail(orderDTO.getUserEmail())).thenReturn(Optional.of(userPrincipal));
        when(itemRepository.findByName("item1")).thenReturn(Optional.of(item1));
        when(orderMapper.toOrder(any(OrderDTO.class))).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.createOrder(orderDTO);

        // Assert
        verify(userRepository).findByEmail(orderDTO.getUserEmail());
        verify(itemRepository).findByName("item1");
        verify(orderMapper).toOrder(orderDTO);
        verify(orderRepository).save(argThat(new OrderMatcher(order)));
        verify(orderMapper).toOrderResponseDTO(order);
        assertNotNull(result);
    }

    @Test
    public void testCreateOrder_UserNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail("test@example.com");

        when(userRepository.findByEmail(orderDTO.getUserEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderDTO));
        verify(userRepository).findByEmail(orderDTO.getUserEmail());
        verify(itemRepository, never()).findByName(anyString());
        verify(orderRepository, never()).save(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }



    @Test
    @Override
    public void readTest() {
        // Arrange
        OrderStore order1 = new OrderStore();
        order1.setOrderId(UUID.randomUUID());
        order1.setStatus("DELIVERED");

        OrderStore order2 = new OrderStore();
        order2.setOrderId(UUID.randomUUID());
        order2.setStatus("RECEIVED");

        List<OrderStore> orders = Stream.of(order1, order2).collect(Collectors.toList());

        OrderResponseDTO orderResponse1 = new OrderResponseDTO();
        orderResponse1.setId(String.valueOf(order1.getOrderId()));
        orderResponse1.setStatus(order1.getStatus());

        OrderResponseDTO orderResponse2 = new OrderResponseDTO();
        orderResponse2.setId(String.valueOf(order2.getOrderId()));
        orderResponse2.setStatus(order2.getStatus());

        List<OrderResponseDTO> expectedResponse = Stream.of(orderResponse1, orderResponse2).collect(Collectors.toList());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(orderResponse1,orderResponse2);


        // Act
        List<OrderResponseDTO> result = orderService.getAllOrders();

        // Assert
        verify(orderRepository).findAll();
        verify(orderMapper).toOrderResponseDTO(order1);
        verify(orderMapper).toOrderResponseDTO(order2);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetOrderByUser() {
        // Arrange
        String userEmail = "test@example.com";
        String userEmailF = "\"" + userEmail + "\"";

        OrderStore order1 = new OrderStore();
        order1.setOrderId(UUID.randomUUID());
        order1.setStatus("DELIVERED");

        OrderStore order2 = new OrderStore();
        order2.setOrderId(UUID.randomUUID());
        order2.setStatus("RECEIVED");

        List<OrderStore> orders = Stream.of(order1, order2).collect(Collectors.toList());

        OrderResponseDTO orderResponse1 = new OrderResponseDTO();
        orderResponse1.setId(String.valueOf(order1.getOrderId()));
        orderResponse1.setStatus(order1.getStatus());

        OrderResponseDTO orderResponse2 = new OrderResponseDTO();
        orderResponse2.setId(String.valueOf(order2.getOrderId()));
        orderResponse2.setStatus(order2.getStatus());

        List<OrderResponseDTO> expectedResponse = Stream.of(orderResponse1, orderResponse2).collect(Collectors.toList());

        when(orderRepository.findByUserPrincipalEmail(userEmail)).thenReturn(orders);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(orderResponse1,orderResponse2);

        // Act
        List<OrderResponseDTO> result = orderService.getOrderByUser(userEmailF);

        // Assert
        verify(orderRepository).findByUserPrincipalEmail(userEmail);
        verify(orderMapper).toOrderResponseDTO(order1);
        verify(orderMapper).toOrderResponseDTO(order2);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetOrderByUser_UserEmailNotFound() {
        // Arrange
        String userEmail = "test@example.com";
        String userEmailF = "\"" + userEmail + "\"";

        when(orderRepository.findByUserPrincipalEmail(any())).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.getOrderByUser(userEmailF));
        verify(orderRepository).findByUserPrincipalEmail(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    @Override
    public void updateTest() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setUserEmail("test@example.com");
        orderDTO.setItems(Collections.singletonList("item1"));
        orderDTO.setStatus("completed");
        orderDTO.setTotal(100L);

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderDTO.getOrderId()));

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setEmail(orderDTO.getUserEmail());

        Item item1 = new Item();
        item1.setName("item1");

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setUserEmail(orderDTO.getUserEmail());

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(itemRepository.findByName("item1")).thenReturn(Optional.of(item1));
        when(userRepository.findByEmail(orderDTO.getUserEmail())).thenReturn(Optional.of(userPrincipal));
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.updateOrder(orderDTO);

        // Assert
        verify(orderRepository, times(1)).findById(order.getOrderId());
        verify(itemRepository, times(1)).findByName("item1");
        verify(userRepository, times(1)).findByEmail(orderDTO.getUserEmail());

        assertNotNull(result);
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(UUID.randomUUID().toString());

        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));
        verify(orderRepository).findById(any());
        verify(itemRepository, never()).findByName(anyString());
        verify(userRepository, never()).findByEmail(anyString());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    public void testUpdateOrder_ItemNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setItems(Collections.singletonList("item1"));

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderDTO.getOrderId()));

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(itemRepository.findByName("item1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));
        verify(orderRepository).findById(order.getOrderId());
        verify(itemRepository).findByName("item1");
        verify(userRepository, never()).findByEmail(anyString());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    public void testUpdateOrder_UserNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setUserEmail("test@example.com");

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderDTO.getOrderId()));

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(userRepository.findByEmail(orderDTO.getUserEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));
        verify(orderRepository).findById(order.getOrderId());
        verify(itemRepository, never()).findByName(anyString());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    @Override
    public void deleteTest() {
        // Arrange
        String orderId = UUID.randomUUID().toString();

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderId));

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setId(String.valueOf(order.getOrderId()));

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository).findById(order.getOrderId());
        verify(orderRepository).delete(order);
        verify(orderMapper).toOrderResponseDTO(order);
        assertNotNull(result);
    }

    @Test
    public void testDeleteOrder_OrderNotFound() {
        // Arrange
        String orderId = UUID.randomUUID().toString();

        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.deleteOrder(orderId));
        verify(orderRepository).findById(any());
        verify(orderRepository, never()).delete(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }


    @Test
    public void testPayOrder() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderId));
        order.setStatus("UNPAID");

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setId(String.valueOf(order.getOrderId()));
        expectedResponse.setStatus("PAID");

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.payOrder(orderIdF);

        // Assert
        verify(orderRepository).findById(order.getOrderId());
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
        assertEquals(expectedResponse, result);
        assertEquals("PAID", order.getStatus());
    }

    @Test
    public void testPayOrder_OrderNotFound() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.payOrder(orderIdF));
        verify(orderRepository).findById(any());
        verify(orderRepository, never()).save(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    public void testDeliverOrder() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderId));
        order.setStatus("PENDING");

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setId(String.valueOf(order.getOrderId()));
        expectedResponse.setStatus("DELIVERED");

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.deliverOrder(orderIdF);

        // Assert
        verify(orderRepository).findById(order.getOrderId());
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
        assertEquals(expectedResponse, result);
        assertEquals("DELIVERED", order.getStatus());
    }

    @Test
    public void testDeliverOrder_OrderNotFound() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.deliverOrder(orderIdF));
        verify(orderRepository).findById(any());
        verify(orderRepository, never()).save(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }

    @Test
    public void testReceiveOrder() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        OrderStore order = new OrderStore();
        order.setOrderId(UUID.fromString(orderId));
        order.setStatus("DELIVERED");

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setId(String.valueOf(order.getOrderId()));
        expectedResponse.setStatus("RECEIVED");

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(any(OrderStore.class))).thenReturn(expectedResponse);

        // Act
        OrderResponseDTO result = orderService.receiveOrder(orderIdF);

        // Assert
        verify(orderRepository).findById(order.getOrderId());
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
        assertEquals(expectedResponse, result);
        assertEquals("RECEIVED", order.getStatus());
    }

    @Test
    public void testReceiveOrder_OrderNotFound() {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String orderIdF = "\"" + orderId + "\"";

        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.receiveOrder(orderIdF));
        verify(orderRepository).findById(any());
        verify(orderRepository, never()).save(any());
        verify(orderMapper, never()).toOrderResponseDTO(any(OrderStore.class));
    }




}
