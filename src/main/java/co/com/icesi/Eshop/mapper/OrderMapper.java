package co.com.icesi.Eshop.mapper;

import co.com.icesi.Eshop.dto.request.OrderDTO;
import co.com.icesi.Eshop.dto.response.OrderResponseDTO;
import co.com.icesi.Eshop.model.OrderStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", ignore = true)
    OrderStore toOrder(OrderDTO orderDTO);
    @Mapping(target = "items", ignore = true)
    OrderStore toOrder(OrderResponseDTO orderResponseDTO);
    @Mapping(target = "items", expression = "java(orderStore.getItems().stream().map(item -> item.getName()).toList())")
    OrderDTO toOrderDTO(OrderStore orderStore);
    @Mapping(target = "items", expression = "java(orderResponseDTO.getItems())")
    OrderDTO toOrderDTO(OrderResponseDTO orderResponseDTO);
    @Mapping(target = "items", expression = "java(orderStore.getItems().stream().map(item -> item.getName()).toList())")
    @Mapping(target = "userEmail", expression = "java(orderStore.getUserPrincipal().getEmail())")
    @Mapping(target = "id", expression = "java(orderStore.getOrderId().toString())")
    OrderResponseDTO toOrderResponseDTO(OrderStore orderStore);
    @Mapping(target = "items", expression = "java(orderDTO.getItems())")
    OrderResponseDTO toOrderResponseDTO(OrderDTO orderDTO);
}
