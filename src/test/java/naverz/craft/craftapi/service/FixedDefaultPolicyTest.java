package naverz.craft.craftapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.mapper.CreditMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class FixedDefaultPolicyTest {

    @Autowired
    FixedDefaultPolicy creditPolicy;

    @Autowired
    private CreditMapper creditMapper;

    @Test
    @DisplayName("무상재화가 충분히 있을 경우, 무상재화만 소비되어야 한다.")
    void only_free_credit() {
        //given
        PlayerInventory playerInventory = new PlayerInventory(5L, 1L, 5);

        //when
        PlayerCredit playerUpdatedCredit = creditPolicy.purchaseItem(playerInventory);
        BigDecimal playerPaidCredit = creditMapper.getPaidCredit(playerInventory.getPlayerId());

        //then
        assertEquals(playerUpdatedCredit.getFreeCredit().compareTo(BigDecimal.ZERO), 1);
        assertEquals(playerUpdatedCredit.getPaidCredit().compareTo(playerPaidCredit), 0);
    }

    @Test
    @DisplayName("무상재화 값보다 더 많은 비용이 필요할 경우, 무상재화는 0으로 변경되며 유상재화가 차감된다.")
    void both_free_and_paid_credit() {
        //given
        PlayerInventory playerInventory = new PlayerInventory(1L, 1L, 8);
        //when
        PlayerCredit playerUpdatedCredit = creditPolicy.purchaseItem(playerInventory);
        BigDecimal playerPaidCredit = creditMapper.getPaidCredit(playerInventory.getPlayerId());

        //then
        assertEquals(playerUpdatedCredit.getFreeCredit().compareTo(BigDecimal.ZERO), 0);
        assertTrue(playerUpdatedCredit.getPaidCredit().compareTo(playerPaidCredit) < 0);
    }

    @Test
    @DisplayName("구매 비용이 총비용과 같을 경우, 유상재화와 무상재화는 모두 0원이 된다.")
    void both_free_and_paid_credit_zero() {
        //given
        PlayerInventory playerInventory = new PlayerInventory(3L, 1L, 4);
        //when
        PlayerCredit playerUpdatedCredit = creditPolicy.purchaseItem(playerInventory);
        BigDecimal playerPaidCredit = creditMapper.getPaidCredit(playerInventory.getPlayerId());

        //then
        assertEquals(playerUpdatedCredit.getFreeCredit().compareTo(BigDecimal.ZERO), 0);
        assertEquals(playerUpdatedCredit.getPaidCredit().compareTo(BigDecimal.ZERO), 0);
    }

    @Test
    @DisplayName("재화 값이 충분치 않을경우 실패 발생")
    void credit_not_enough() {
        //given
        PlayerInventory playerInventory = new PlayerInventory(1L, 1L, 100);

        //then
        assertThrows(RuntimeException.class, () -> creditPolicy.purchaseItem(playerInventory));
    }
}