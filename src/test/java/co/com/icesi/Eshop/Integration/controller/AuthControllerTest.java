package co.com.icesi.Eshop.Integration.controller;

import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.dto.security.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String URL="/api/authorities/login";


    @Test
    public void testLogin() throws Exception {
        var login =   mockMvc.perform(MockMvcRequestBuilders.post(URL).content(
                                objectMapper.writeValueAsString(LoginDTO.builder().username("email1@email.com").password("password").build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = objectMapper.readTree(login.getResponse().getContentAsString());

        System.out.println("Token response: "+response.get("token").asText());
        System.out.println("Current role: "+response.get("role").asText());
    }

    @Test
    public void testLoginBadCredentials() throws Exception {
        var login =   mockMvc.perform(MockMvcRequestBuilders.post(URL).content(
                                objectMapper.writeValueAsString(LoginDTO.builder().username("email1@email.com.es").password("assword").build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
        System.out.println(login.getResponse().getContentAsString());
    }

    @Test
    public void testLoginBlankUsername() throws Exception {
        var login =   mockMvc.perform(MockMvcRequestBuilders.post(URL).content(
                                objectMapper.writeValueAsString(LoginDTO.builder().username("").password("password").build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(login.getResponse().getContentAsString());
    }

    @Test
    public void testLoginBlankPassword() throws Exception {
        var login =   mockMvc.perform(MockMvcRequestBuilders.post(URL).content(
                                objectMapper.writeValueAsString(LoginDTO.builder().username("email1@email.com").password("").build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(login.getResponse().getContentAsString());
    }
}
