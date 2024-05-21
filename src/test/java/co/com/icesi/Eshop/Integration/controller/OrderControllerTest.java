package co.com.icesi.Eshop.Integration.controller;

import co.com.icesi.Eshop.Integration.util.CRUD;
import co.com.icesi.Eshop.Integration.util.TestConfigurationData;
import co.com.icesi.Eshop.Integration.util.TokenGenerator;
import co.com.icesi.Eshop.dto.request.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfigurationData.class )
@ActiveProfiles(profiles = "test")
@Transactional
@Rollback
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token = "";

    private final String URL = "/api/orders/";

    @BeforeEach
    public  void init(){
        token = TokenGenerator.getToken(mockMvc,objectMapper,"email3@email.com");
    }



    @Test
    public void TestCreateOrder() throws Exception {

        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(defaultOrderDTO())
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestCreateOrder_blank_userEmail() throws Exception {
        var order = defaultOrderDTO();
        order.setUserEmail("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestCreateOrder_blank_status() throws Exception {
        var order = defaultOrderDTO();
        order.setStatus("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestCreateOrder_less_zero_total() throws Exception {
        var order = defaultOrderDTO();
        order.setTotal(-1L);
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestCreateOrder_null_list() throws Exception {
        var order = defaultOrderDTO();
        order.setItems(null);
        var  result = mockMvc.perform(MockMvcRequestBuilders.post(URL+ CRUD.C.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        OrderDTO orderDTO = defaultOrderDTO();
        orderDTO.setOrderId("a35184d4-ff13-11ed-be56-0242ac120004");
        orderDTO.setStatus("DELIVERED");
        System.out.println("Current token: "+ token);
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(orderDTO)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void TestUpdateOrder_blank_userEmail() throws Exception {
        var order = defaultOrderDTO();
        order.setUserEmail("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestUpdate_blank_status() throws Exception {
        var order = defaultOrderDTO();
        order.setStatus("");
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestUpdateOrder_less_zero_total() throws Exception {
        var order = defaultOrderDTO();
        order.setTotal(-1L);
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void TestUpdateOrder_null_list() throws Exception {
        var order = defaultOrderDTO();
        order.setItems(null);
        var  result = mockMvc.perform(MockMvcRequestBuilders.put(URL+ CRUD.U.getAction()).content(
                                objectMapper.writeValueAsString(order)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        System.out.println("Current token: "+ token);
        String orderID = "a35184d4-ff13-11ed-be56-0242ac120003";
        var  result = mockMvc.perform(MockMvcRequestBuilders.delete(URL+ CRUD.D.getAction())
                        .content(orderID)
                        .header("Authorization", "Bearer " + token)

                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetOrders() throws Exception {
        System.out.println("Current token: "+ token);
        var  result = mockMvc.perform(MockMvcRequestBuilders.get(URL+ CRUD.R.getAction())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testPayOrder() throws Exception {
        System.out.println("Current token: "+ token);
        String orderID = "a35184d4-ff13-11ed-be56-0242ac120002";
        var  result = mockMvc.perform(MockMvcRequestBuilders.patch(URL+"pay")
                        .content(orderID)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeliverOrder() throws Exception {
        System.out.println("Current token: "+ token);
        String orderID = "a35184d4-ff13-11ed-be56-0242ac120002";
        var  result = mockMvc.perform(MockMvcRequestBuilders.patch(URL+ "deliver")
                        .content(orderID)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testReceiveOrder() throws Exception {
        System.out.println("Current token: "+ token);
        String orderID = "a35184d4-ff13-11ed-be56-0242ac120002";
        var  result = mockMvc.perform(MockMvcRequestBuilders.patch(URL+"receive")
                        .content(orderID)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetByUser () throws  Exception{
        String userName = "email2@email.com";
        var  result = mockMvc.perform(MockMvcRequestBuilders.patch(URL+"getByUser")
                        .content(userName)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    public OrderDTO defaultOrderDTO(){
        return OrderDTO.builder()
                .userEmail("email3@email.com")
                .status("PENDING")
                .total(100L)
                .items(List.of("Item 1","Item 2"))
                .build();
    }
}
