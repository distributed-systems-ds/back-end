package co.com.icesi.Eshop.controller;

import co.com.icesi.Eshop.api.OrderApi;
import co.com.icesi.Eshop.dto.request.OrderDTO;
import co.com.icesi.Eshop.dto.response.OrderResponseDTO;
import co.com.icesi.Eshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;
    /**
     * @param orderDTO
     * @return
     */
    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    /**
     * @param orderDTO
     * @return
     */
    @Override
    public OrderResponseDTO updateOrder(OrderDTO orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

    @Override
    public OrderResponseDTO delete(String orderName) {
        return orderService.deleteOrder(orderName);
    }

    /**
     * @param orderDTO
     * @return
     * public OrderResponseDTO cancelOrder(OrderDTO orderDTO) {
     *         return null;
     *     }
     */



    /**
     * @param orderName
     * @return
     */
    @Override
    public OrderResponseDTO payOrder(String orderName) {
        return orderService.payOrder(orderName);
    }

    /**
     * @param
     *
     * @return
     */
    @Override
    public OrderResponseDTO deliverOrder(String orderName) {
        return orderService.deliverOrder(orderName);
    }

    /**
     * @param orderName
     * @return
     */
    @Override
    public OrderResponseDTO receiveOrder(String orderName) {
        return orderService.receiveOrder(orderName);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(String userName) {
        return orderService.getOrderByUser(userName);
    }

    /**
     * @return
     */
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
