package naverz.craft.craftapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import naverz.craft.craftapi.domain.PlayerInventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("수량이 0보다 작을 경우 예외 발생")
    public void quantity_smaller_than_zero() throws Exception {
        PlayerInventory playerInventoryRequest = PlayerInventory.builder()
            .playerId(1L)
            .itemId(1L)
            .quantity(-1).build();
        String requsetJsonString = objectMapper.writeValueAsString(playerInventoryRequest);

        mockMvc.perform(post("/api/orders/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requsetJsonString))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("수량이 0보다 클경우 정상 처리")
    public void quantity_bigger_than_zero() throws Exception {
        PlayerInventory playerInventoryRequest = PlayerInventory.builder()
            .playerId(1L)
            .itemId(1L)
            .quantity(3).build();
        String requsetJsonString = objectMapper.writeValueAsString(playerInventoryRequest);

        mockMvc.perform(post("/api/orders/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requsetJsonString))
            .andExpect(status().isOk())
            .andDo(print());
    }
}