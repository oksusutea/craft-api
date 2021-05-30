package naverz.craft.craftapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.mapper.CreditMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class CreditServiceTest {

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private CreditService creditService;

    @Test
    @DisplayName("무상재화 충전 성공")
    void chargeFreeSuccess() {
        //given
        PlayerCredit creditRequest = new PlayerCredit(1L, BigDecimal.ZERO, BigDecimal.ONE);
        BigDecimal beforeFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal beforePaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //when
        BigDecimal totalCredit = creditService.chargeCredit(creditRequest);
        BigDecimal afterFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal afterPaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //then
        assertEquals(beforeFreeCredit.add(creditRequest.getFreeCredit()), afterFreeCredit);
        assertEquals(beforePaidCredit, afterPaidCredit);

    }

    @Test
    @DisplayName("유상재화 충전 성공")
    void chargePaidSuccess() {
        //given
        PlayerCredit creditRequest = new PlayerCredit(1L, BigDecimal.ONE, BigDecimal.ZERO);
        BigDecimal beforeFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal beforePaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //when
        BigDecimal totalCredit = creditService.chargeCredit(creditRequest);
        BigDecimal afterFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal afterPaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //then
        assertEquals(beforeFreeCredit, afterFreeCredit);
        assertEquals(beforePaidCredit.add(creditRequest.getPaidCredit()), afterPaidCredit);
    }

    @Test
    @DisplayName("무상, 유상 둘 다 충전 성공")
    void chargeBothSuccess() {
        //given
        PlayerCredit creditRequest = new PlayerCredit(1L, BigDecimal.ONE, BigDecimal.ONE);
        BigDecimal beforeFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal beforePaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //when
        BigDecimal totalCredit = creditService.chargeCredit(creditRequest);
        BigDecimal afterFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal afterPaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());

        //then
        assertEquals(beforeFreeCredit.add(creditRequest.getFreeCredit()), afterFreeCredit);
        assertEquals(beforePaidCredit.add(creditRequest.getPaidCredit()), afterPaidCredit);
    }

    @Test
    @DisplayName("충전후 총 비용 계산 성공")
    void calculateAfterChargeSuccess() {
        //given
        final BigDecimal addFreeCredit = BigDecimal.ONE;
        final BigDecimal addPaidCredit = BigDecimal.TEN;
        PlayerCredit creditRequest = new PlayerCredit(1L, addPaidCredit, addFreeCredit);
        BigDecimal beforeFreeCredit = creditMapper.getFreeCredit(creditRequest.getPlayerId());
        BigDecimal beforePaidCredit = creditMapper.getPaidCredit(creditRequest.getPlayerId());
        BigDecimal beforeTotalCredit = beforeFreeCredit.add(beforePaidCredit);

        //when
        BigDecimal totalCredit = creditService.chargeCredit(creditRequest);

        //then
        assertEquals(beforeTotalCredit.add(addFreeCredit).add(addPaidCredit), totalCredit);
    }
}