package co.com.icesi.Eshop.Integration.controller;

import co.com.icesi.Eshop.Integration.util.CRUD;
import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.Integration.util.TokenGenerator;
import co.com.icesi.Eshop.dto.request.UserDTO;
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
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token = "";

    private final String URL = "/api/users/";

    @BeforeEach
    public  void init(){
        token = TokenGenerator.getToken(mockMvc,objectMapper,"email1@email.com");
    }



    @Test
    public void testCreateUser() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(defaultUserPrincipal())
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testCreateUse_blankEmail() throws Exception {
        var user = defaultUserPrincipal();
        user.setEmail("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateUse_Not_format_Email() throws Exception {
        var user = defaultUserPrincipal();
        user.setEmail("sasuke.com");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateUse_blankPassword() throws Exception {
        var user = defaultUserPrincipal();
        user.setPassword("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateUse_blankPhoneNumber() throws Exception {
        var user = defaultUserPrincipal();
        user.setPhoneNumber("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateUse_blankLastName() throws Exception {
        var user = defaultUserPrincipal();
        user.setLastName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateUse_blankRole() throws Exception {
        var user = defaultUserPrincipal();
        user.setRole("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO updatedUser = defaultUserPrincipal();
        updatedUser.setEmail("email2@email.com");
        updatedUser.setPhoneNumber("313856232881");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(updatedUser)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }



    @Test
    public void testUpdateUser_blankEmail() throws Exception {
        var user = defaultUserPrincipal();
        user.setEmail("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUse_Not_format_Email() throws Exception {
        var user = defaultUserPrincipal();
        user.setEmail("sasuke.com");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUse_blankPassword() throws Exception {
        var user = defaultUserPrincipal();
        user.setPassword("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUse_blankPhoneNumber() throws Exception {
        var user = defaultUserPrincipal();
        user.setPhoneNumber("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUse_blankLastName() throws Exception {
        var user = defaultUserPrincipal();
        user.setLastName("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUse_blankRole() throws Exception {
        var user = defaultUserPrincipal();
        user.setRole("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(user)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteUser() throws Exception {
        String email = "email2@email.com";
        var  result = mockMvc.perform(MockMvcRequestBuilders.delete(URL+ CRUD.D.getAction())
                        .content(email)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetUsers() throws Exception {
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ CRUD.R.getAction())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    private UserDTO defaultUserPrincipal(){
        return UserDTO.builder()
                .email("emailtest@email.com")
                .password("password")
                .phoneNumber("3123842122")
                .firstName("Campaz")
                .lastName("Sasuke")
                .role("USER")
                .build();


    }
}
