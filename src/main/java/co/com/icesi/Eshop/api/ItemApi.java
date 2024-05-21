package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.ItemDTO;
import co.com.icesi.Eshop.dto.response.ItemResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(ItemApi.BASE_URL)
public interface ItemApi {
    String BASE_URL = "/api/items";

    @PostMapping("/create")
    ItemResponseDTO createItem(@Valid @RequestBody ItemDTO itemResponseDTO);

    @PutMapping("/update")
    ItemResponseDTO updateItem( @Valid @RequestBody ItemDTO itemResponseDTO);

    @DeleteMapping("/delete")
    String deleteItem(@RequestBody String itemName);

    @GetMapping("/find/{itemName}")
    ItemResponseDTO findItem(@PathVariable String itemName);

    @GetMapping("/all")
    Iterable<ItemResponseDTO> allItems();

}
