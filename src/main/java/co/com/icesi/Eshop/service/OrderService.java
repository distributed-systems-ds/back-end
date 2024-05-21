package co.com.icesi.Eshop.service;

import co.com.icesi.Eshop.dto.request.OrderDTO;
import co.com.icesi.Eshop.dto.response.OrderResponseDTO;
import co.com.icesi.Eshop.mapper.OrderMapper;
import co.com.icesi.Eshop.model.OrderStore;
import co.com.icesi.Eshop.repository.ItemRepository;
import co.com.icesi.Eshop.repository.OrderRepository;
import co.com.icesi.Eshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        OrderStore order = orderMapper.toOrder(orderDTO);
        order.setUserPrincipal(userRepository.findByEmail(orderDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("UserPrincipal not found")));
        order.setItems(orderDTO.getItems().stream().map(itemRepository::findByName).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));
        order.setOrderId(UUID.randomUUID());
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO updateOrder(OrderDTO orderDTO) {
        OrderStore order = orderRepository.findById(UUID.fromString(orderDTO.getOrderId())).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setItems(orderDTO.getItems().stream().map(itemRepository::findByName).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));
        order.setStatus(orderDTO.getStatus());
        order.setTotal(orderDTO.getTotal());
        order.setUserPrincipal(userRepository.findByEmail(orderDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("UserPrincipal not found")));
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO deleteOrder(String orderId) {
        OrderStore order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
        return orderMapper.toOrderResponseDTO(order);
    }
    public OrderResponseDTO payOrder(String orderId) {
        String orderIdF = orderId;
        if(orderIdF.matches(".*\".*")){
            orderIdF = orderId.substring(1, orderId.length() - 1);
        }
        OrderStore order = orderRepository.findById(UUID.fromString(orderIdF)).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("PAID");
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO cancelOrder(String orderId) {
        String orderIdF = orderId.substring(1, orderId.length()-1);
        OrderStore order = orderRepository.findById(UUID.fromString(orderIdF)).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("CANCELED");
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO deliverOrder(String orderId) {
        String orderIdF = orderId;
        if(orderIdF.matches(".*\".*")){
            orderIdF = orderId.substring(1, orderId.length() - 1);
        }
        OrderStore order = orderRepository.findById(UUID.fromString(orderIdF)).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("DELIVERED");
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO receiveOrder(String orderId) {
        String orderIdF = orderId;
        if(orderIdF.matches(".*\".*")){
            orderIdF = orderId.substring(1, orderId.length() - 1);
        }
        OrderStore order = orderRepository.findById(UUID.fromString(orderIdF)).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("RECEIVED");
        return orderMapper.toOrderResponseDTO(orderRepository.save(order));
    }
    public OrderResponseDTO getOrderById(String orderId) {
        return orderMapper.toOrderResponseDTO(orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new RuntimeException("Order not found")));
    }
    public List<OrderResponseDTO> getOrderByUser(String userEmail) {
        String userEmailF = userEmail;
        if(userEmailF.matches(".*\".*")){
            userEmailF = userEmail.substring(1, userEmail.length() - 1);
        }
        return orderRepository.findByUserPrincipalEmail(userEmailF).stream().map(orderMapper::toOrderResponseDTO).collect(Collectors.toList());
    }
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponseDTO).collect(Collectors.toList());
    }
}
