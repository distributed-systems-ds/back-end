package co.com.icesi.Eshop.Integration.controller;

import co.com.icesi.Eshop.Integration.util.CRUD;
import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.Integration.util.TokenGenerator;
import co.com.icesi.Eshop.dto.request.CategoryDTO;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfigurationData.class )
@ActiveProfiles(profiles = "test")
@Transactional
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token = "";



    private final String URL = "/api/categories/";

    @BeforeEach
    public  void init(){
        token = TokenGenerator.getToken(mockMvc,objectMapper,"email1@email.com");
    }

    @Test
    public void testCreateCategory() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(defaultCategory())
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateCategory_blankName() throws Exception {
        var category = defaultCategory();
        category.setName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(category)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateCategory_blankDescription() throws Exception {
        var category = defaultCategory();
        category.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(category)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = defaultCategory();
        categoryDTO.setName("Category 3");
        categoryDTO.setDescription("New description");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(categoryDTO)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testUpdateCategory_blankName() throws Exception {
        var category = defaultCategory();
        category.setName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(category)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateCategory_blankDescription() throws Exception {
        var category = defaultCategory();
        category.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(category)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        String category = "Category 4";
        var  result = mockMvc.perform(MockMvcRequestBuilders.delete(URL+ CRUD.D.getAction())
                        .content(category)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetCategory() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ CRUD.R.getAction())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    public CategoryDTO defaultCategory(){
        return CategoryDTO.builder()
                .name("Category test")
                .description("Description for category test")
                .build();
    }



}
