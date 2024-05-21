package co.com.icesi.Eshop.Integration.controller;


import co.com.icesi.Eshop.Integration.util.CRUD;
import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.Integration.util.TokenGenerator;
import co.com.icesi.Eshop.dto.request.RoleDTO;
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
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token = "";

    private final String URL = "/api/roles/";

    @BeforeEach
    public  void init(){
        token = TokenGenerator.getToken(mockMvc,objectMapper,"email1@email.com");
    }



    @Test
    public void testCreateRole() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(defaultRole())
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateRole_blank_roleName() throws Exception {
        var role = defaultRole();
        role.setRoleName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(role)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateRole_blank_description() throws Exception {
        var role = defaultRole();
        role.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(role)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateRole() throws Exception {
        RoleDTO roleDTO = defaultRole();
        roleDTO.setRoleName("USER");
        roleDTO.setDescription("New description");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(roleDTO)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testUpdate_blank_roleName() throws Exception {
        var role = defaultRole();
        role.setRoleName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(role)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdate_blank_description() throws Exception {
        var role = defaultRole();
        role.setDescription("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(role)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testDeleteRole() throws Exception {
        String role = "DELETE";
        var  result = mockMvc.perform(MockMvcRequestBuilders.delete(URL+ CRUD.D.getAction())
                        .content(role)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetRoles() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ CRUD.R.getAction())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    public RoleDTO defaultRole(){
        return RoleDTO.builder()
                .roleName("TEST")
                .description("Administrator")
                .build();
    }


}
