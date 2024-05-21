package co.com.icesi.Eshop.controller;

import co.com.icesi.Eshop.api.ItemApi;
import co.com.icesi.Eshop.dto.request.ItemDTO;
import co.com.icesi.Eshop.dto.response.ItemResponseDTO;
import co.com.icesi.Eshop.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ItemController implements ItemApi {
    private final ItemService itemService;
    /**
     * @param itemDTO
     * @return
     */
    @Override
    public ItemResponseDTO createItem(ItemDTO itemDTO) {
        return itemService.createItem(itemDTO);
    }

    /**
     * @param itemDTO
     * @return
     */
    @Override
    public ItemResponseDTO updateItem(ItemDTO itemDTO) {
        return itemService.updateItem(itemDTO);
    }

    /**
     * @param itemName
     * @return
     */
    @Override
    public String deleteItem(String itemName) {
        return itemService.deleteItem(itemName);
    }

    /**
     * @param itemName
     * @return
     */
    @Override
    public ItemResponseDTO findItem(String itemName) {
        return itemService.findItem(itemName);
    }

    /**
     * @return
     */
    @Override
    public Iterable<ItemResponseDTO> allItems() {
        return itemService.getAllItems();
    }
}
