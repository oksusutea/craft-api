package naverz.craft.craftapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import naverz.craft.craftapi.domain.PlayerCredit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("재화 충전 금액이 0보다 작을 경우 예외 발생")
    public void credit_smaller_than_zero() throws Exception {
        PlayerCredit creditRequest = PlayerCredit.builder().playerId(1L)
            .freeCredit(BigDecimal.valueOf(-10L))
            .paidCredit(BigDecimal.ZERO)
            .build();

        String requsetJsonString = objectMapper.writeValueAsString(creditRequest);

        mockMvc.perform(post("/api/credits/charge")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requsetJsonString))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("정상 금액시 요청 응답 성공")
    public void credit_bigger_than_zero() throws Exception {
        PlayerCredit creditRequest = PlayerCredit.builder().playerId(1L)
            .freeCredit(BigDecimal.TEN)
            .paidCredit(BigDecimal.ZERO)
            .build();

        String requsetJsonString = objectMapper.writeValueAsString(creditRequest);

        mockMvc.perform(post("/api/credits/charge")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requsetJsonString))
            .andExpect(status().isOk())
            .andDo(print());
    }
}