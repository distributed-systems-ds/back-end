package co.com.icesi.Eshop.mapper;

import co.com.icesi.Eshop.dto.request.ItemDTO;
import co.com.icesi.Eshop.dto.response.ItemResponseDTO;
import co.com.icesi.Eshop.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(target = "category", ignore = true)
    Item toItem(ItemDTO itemDTO);
    @Mapping(target = "category", ignore = true)
    Item toItem(ItemResponseDTO itemResponseDTO);
    @Mapping(target = "category", expression = "java(item.getCategory().getName())")
    ItemDTO toItemDTO(Item item);

    ItemDTO toItemDTO(ItemResponseDTO itemResponseDTO);
    @Mapping(target = "category", expression = "java(item.getCategory().getName())")
    ItemResponseDTO toItemResponseDTO(Item item);

    ItemResponseDTO toItemResponseDTO(ItemDTO itemDTO);
}
