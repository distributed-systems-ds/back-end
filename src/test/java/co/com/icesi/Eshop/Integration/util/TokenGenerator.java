package co.com.icesi.Eshop.Integration.util;
import co.com.icesi.Eshop.dto.security.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(profiles = "test")
@Import(TestConfigurationData.class )
public class TokenGenerator {
    @SneakyThrows
    public static String getToken(MockMvc mockMvc, ObjectMapper objectMapper , String email) {
        var login =   mockMvc.perform(MockMvcRequestBuilders.post("/api/authorities/login").content(
                                objectMapper.writeValueAsString(LoginDTO.builder().username(email).password("password").build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = objectMapper.readTree(login.getResponse().getContentAsString());

        System.out.println(response.get("role").asText()+" este es el rol actual");

        return response.get("token").asText();
    }
}
