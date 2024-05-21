package co.com.icesi.Eshop.Integration.controller;

import co.com.icesi.Eshop.Integration.util.CRUD;
import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.Integration.util.TokenGenerator;
import co.com.icesi.Eshop.dto.request.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfigurationData.class )
@ActiveProfiles(profiles = "test")
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token = "";

    private final String URL = "/api/items/";

    @BeforeEach
    public  void init(){
        token = TokenGenerator.getToken(mockMvc,objectMapper,"email1@email.com");
    }


    @Test
    public void testCreateItem() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(defaulItem())
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateItem_blankName() throws Exception {
        var item = defaulItem();
        item.setName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testCreateItem_blankDescription() throws Exception {
        var item = defaulItem();
        item.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateItem_LessThanZeroPrice() throws Exception {
        var item = defaulItem();
        item.setPrice(-1L);
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateItem_BlankCategory() throws Exception {
        var item = defaulItem();
        item.setCategory("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateItem() throws Exception {
        ItemDTO itemDTO = defaulItem();
        //assert itemDTO != null;
        itemDTO.setName("Item 2");
        itemDTO.setDescription("New description");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(itemDTO)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }



    @Test
    public void testUpdateItem_blankName() throws Exception {
        var item = defaulItem();
        item.setName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testUpdateItem_blankDescription() throws Exception {
        var item = defaulItem();
        item.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateItem_LessThanZeroPrice() throws Exception {
        var item = defaulItem();
        item.setPrice(-1L);
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateItem_BlankCategory() throws Exception {
        var item = defaulItem();
        item.setCategory("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(item)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDelete() throws Exception {
        String itemName = "Item 5";
        var  result = mockMvc.perform(MockMvcRequestBuilders.delete(URL+ CRUD.D.getAction())
                        .content(itemName)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetItem() throws Exception {
        String itemName = "Item 2";
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ "find/{itemName}",itemName)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetItems() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ CRUD.R.getAction())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    private ItemDTO defaulItem(){
        return ItemDTO.builder()
                .name("Item test")
                .description("Description test")
                .imageUrl("https://via.placeholder.com/150")
                .price(1000L)
                .orderId("a35182a4-ff13-11ed-be56-0242ac120002")
                .category("Category 4").build();
    }
}
