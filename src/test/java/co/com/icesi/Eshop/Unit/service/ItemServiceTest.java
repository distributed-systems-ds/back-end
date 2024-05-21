package co.com.icesi.Eshop.Unit.service;

import co.com.icesi.Eshop.Unit.util.CrudTest;
import co.com.icesi.Eshop.Unit.util.Matcher.ItemMatcher;
import co.com.icesi.Eshop.dto.request.ItemDTO;
import co.com.icesi.Eshop.dto.response.ItemResponseDTO;
import co.com.icesi.Eshop.mapper.ItemMapper;
import co.com.icesi.Eshop.mapper.ItemMapperImpl;
import co.com.icesi.Eshop.model.Category;
import co.com.icesi.Eshop.model.Item;
import co.com.icesi.Eshop.model.OrderStore;
import co.com.icesi.Eshop.repository.CategoryRepository;
import co.com.icesi.Eshop.repository.ItemRepository;
import co.com.icesi.Eshop.repository.OrderRepository;
import co.com.icesi.Eshop.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest  implements CrudTest {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemMapper itemMapper;

    private OrderRepository orderRepository;

    private ItemService itemService;

    @BeforeEach
    public void init(){
        itemRepository = mock(ItemRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        itemMapper = spy(ItemMapperImpl.class);
        orderRepository = mock(OrderRepository.class);
        itemService = new ItemService(itemRepository, orderRepository, categoryRepository, itemMapper);


    }

    @Test
    @Override
    public void createTest() {
        // Arrange
        ItemDTO itemDTO = defaulItemDTO();

        Item item = defaultItem();

        Category category = defaultCategory();

        Item savedItem = new Item();

        savedItem.setName(itemDTO.getName());
        savedItem.setDescription(itemDTO.getDescription());
        savedItem.setCategory(category);

        ItemResponseDTO expectedResponse = new ItemResponseDTO();
        expectedResponse.setName(itemDTO.getName());
        expectedResponse.setDescription(itemDTO.getDescription());

        when(itemMapper.toItem(itemDTO)).thenReturn(item);
        when(categoryRepository.findByName(any())).thenReturn(Optional.of(category));
        when(itemRepository.save(item)).thenReturn(savedItem);
        when(itemMapper.toItemResponseDTO(savedItem)).thenReturn(expectedResponse);

        // Act
        itemService.createItem(itemDTO);

        // Assert
        verify(itemMapper, times(1)).toItem(itemDTO);
        verify(itemRepository, times(1)).save(argThat(new ItemMatcher(item)));
        verify(itemMapper, times(1)).toItemResponseDTO(savedItem);

    }


    @Test
    public void testCreateItem_CategoryNotFound() {
        // Arrange
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("ItemName");
        itemDTO.setDescription("ItemDescription");
        itemDTO.setCategory("Any");

        when(categoryRepository.findByName(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> itemService.createItem(itemDTO));
        verify(itemRepository, never()).save(any());
        verify(itemMapper, never()).toItemResponseDTO(any(Item.class));
    }


    @Test
    @Override
    public void readTest() {
        // Arrange
        Item item1 = defaultItem();
        item1.setName("Item 1");

        Item item2 = defaultItem();
        item2.setName("Item 2");

        List<Item> itemList = Arrays.asList(item1, item2);

        ItemResponseDTO itemResponse1 = new ItemResponseDTO();
        itemResponse1.setName("Item 1");

        ItemResponseDTO itemResponse2 = new ItemResponseDTO();
        itemResponse2.setName("Item 2");

        List<ItemResponseDTO> expectedResponseList = Arrays.asList(itemResponse1, itemResponse2);

        when(itemRepository.findAll()).thenReturn(itemList);
        when(itemMapper.toItemResponseDTO(item1)).thenReturn(itemResponse1);
        when(itemMapper.toItemResponseDTO(item2)).thenReturn(itemResponse2);

        // Act
        List<ItemResponseDTO> result = itemService.getAllItems();

        // Assert
        verify(itemRepository, times(1)).findAll();
        verify(itemMapper, times(1)).toItemResponseDTO(item1);
        verify(itemMapper, times(1)).toItemResponseDTO(item2);
        assertEquals(expectedResponseList, result);
    }

    @Test
    public void testFindItem() {
        // Arrange
        String itemName = "ItemName";

        Item item = defaultItem();
        item.setName(itemName);

        ItemResponseDTO expectedResponse = new ItemResponseDTO();
        expectedResponse.setCategory(defaultCategory().getName());
        expectedResponse.setName(itemName);

        when(itemRepository.findByName(itemName)).thenReturn(Optional.of(item));
        when(itemMapper.toItemResponseDTO(item)).thenReturn(expectedResponse);

        // Act
        ItemResponseDTO result = itemService.findItem(itemName);

        // Assert
        verify(itemRepository, times(1)).findByName(itemName);
        verify(itemMapper, times(1)).toItemResponseDTO(item);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testFindItem_ItemNotFound() {
        // Arrange
        String itemName = "Non-existent Item";

        when(itemRepository.findByName(itemName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> itemService.findItem(itemName));
        verify(itemRepository).findByName(itemName);
        verify(itemMapper, never()).toItemResponseDTO(any(Item.class));
    }

    @Test
    @Override
    public void updateTest() {
        // Arrange
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("ItemName");
        itemDTO.setDescription("ItemDescription");
        itemDTO.setImageUrl("ItemURL");
        itemDTO.setPrice(777L);
        itemDTO.setCategory(defaulItemDTO().getCategory());

        Item item = defaultItem();

        Category category = new Category();
        category.setName(itemDTO.getCategory());

        Item updatedItem = new Item();
        updatedItem.setItemId(defaultItem().getItemId());
        updatedItem.setName(itemDTO.getName());
        updatedItem.setDescription(itemDTO.getDescription());
        updatedItem.setImageUrl(itemDTO.getImageUrl());
        updatedItem.setPrice(itemDTO.getPrice());
        updatedItem.setCategory(category);

        ItemResponseDTO expectedResponse = new ItemResponseDTO();
        expectedResponse.setName(itemDTO.getName());
        expectedResponse.setDescription(itemDTO.getDescription());
        expectedResponse.setImageUrl(itemDTO.getImageUrl());
        expectedResponse.setPrice(itemDTO.getPrice());

        when(itemRepository.findByName(itemDTO.getName())).thenReturn(Optional.of(item));
        when(categoryRepository.findByName(any())).thenReturn(Optional.of(category));
        when(itemRepository.save(updatedItem)).thenReturn(updatedItem);
        when(itemMapper.toItemResponseDTO(updatedItem)).thenReturn(expectedResponse);

        // Act
        ItemResponseDTO result = itemService.updateItem(itemDTO);

        // Assert
        verify(itemRepository, times(1)).findByName(itemDTO.getName());
        verify(categoryRepository, times(1)).findByName(itemDTO.getCategory());
        verify(itemRepository, times(1)).save(argThat(new ItemMatcher(updatedItem)));
        verify(itemMapper, times(1)).toItemResponseDTO(updatedItem);

        assertNotNull(result);
    }

    @Test
    @Override
    public void deleteTest() {
        // Arrange
        String itemName = "ItemName";

        Item item = defaultItem();
        item.setName(itemName);

        List<OrderStore> orders = new ArrayList<>();

        when(itemRepository.findByName(itemName)).thenReturn(Optional.of(item));
        when(orderRepository.findAll()).thenReturn(orders);
        doNothing().when(itemRepository).delete(item);

        // Act
        String result = itemService.deleteItem(itemName);

        // Assert
        verify(itemRepository).findByName(itemName);
        verify(orderRepository).findAll();
        verify(itemRepository).delete(item);
        assertEquals("Item deleted", result);
    }


    @Test
    public void testDeleteItem_ItemNotFound() {
        // Arrange
        String itemName = "Non-existent Item";

        when(itemRepository.findByName(itemName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> itemService.deleteItem(itemName));
        verify(itemRepository).findByName(itemName);
        verify(orderRepository, never()).findAll();
        verify(itemRepository, never()).delete(any());
    }

    @Test
    public void testDeleteItem_ItemInAnOrder() {
        // Arrange
        String itemName = "ItemName";

        Item item = defaultItem();
        item.setName(itemName);

        OrderStore order = defaultOrder();
        order.setItems(List.of(item));

        List<OrderStore> orders = List.of(order);

        when(itemRepository.findByName(itemName)).thenReturn(Optional.of(item));
        when(orderRepository.findAll()).thenReturn(orders);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> itemService.deleteItem(itemName));
        verify(itemRepository).findByName(itemName);
        verify(orderRepository).findAll();
        verify(itemRepository, never()).delete(any());
    }


}
