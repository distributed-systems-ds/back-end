package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.OrderDTO;
import co.com.icesi.Eshop.dto.response.OrderResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(OrderApi.BASE_URL)
public interface OrderApi {
    String BASE_URL = "/api/orders";

    @PostMapping("/create")
    OrderResponseDTO createOrder(@Valid @RequestBody  OrderDTO orderResponseDTO);

    @PutMapping("/update")
    OrderResponseDTO updateOrder( @Valid @RequestBody OrderDTO orderResponseDTO);

    @DeleteMapping("/delete")
    OrderResponseDTO delete(@RequestBody String orderName);



//    @PostMapping("/cancel") si cancela no deberia llegar al backend
    //OrderResponseDTO cancelOrder(OrderDTO orderResponseDTO);

    @PatchMapping("/pay")
    OrderResponseDTO payOrder(@RequestBody String orderName);

    @PatchMapping("/deliver")
    OrderResponseDTO deliverOrder(@RequestBody String orderName);

    @PatchMapping("/receive")
    OrderResponseDTO receiveOrder(@RequestBody String orderName);

    @PatchMapping("/getByUser")
    List<OrderResponseDTO> getOrdersByUser(@RequestBody String userName);

    @GetMapping("/all")
    List<OrderResponseDTO> getAllOrders();
}
