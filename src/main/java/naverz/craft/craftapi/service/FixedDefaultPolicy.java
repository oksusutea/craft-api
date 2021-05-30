package naverz.craft.craftapi.service;

import java.math.BigDecimal;
import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.error.exception.CreditNotEnoughException;
import naverz.craft.craftapi.mapper.CreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * FixedDefaultPolicy :
 *  - 항상 무상 재화가 먼저 소진된다. 무상 재화가 전부 소진되어야 유상 재화가 소진된다.
 *  - 아이템 구매로 재화 소진시, 무상 재화가 부족하면 무상 재화를 전부 소진하고 모자란 금액만큼 유상 재화가 소진된다.
 */

@Service
public class FixedDefaultPolicy implements CreditPolicy {

    @Autowired
    CreditMapper creditMapper;

    @Autowired
    ItemService itemService;

    @Override
    public PlayerCredit purchaseItem(PlayerInventory playerInventoryRequest) {
        long playerId = playerInventoryRequest.getPlayerId();
        BigDecimal freeCredit = creditMapper.getFreeCredit(playerId);
        BigDecimal paidCredit = creditMapper.getPaidCredit(playerId);
        BigDecimal totalAccount = itemService.getItemPrice(playerInventoryRequest.getItemId())
            .multiply(BigDecimal.valueOf(playerInventoryRequest.getQuantity()));

        if (totalAccount.compareTo(freeCredit.add(paidCredit)) > 0) {
            throw new CreditNotEnoughException();
        }

        /* 무상 재화 값 업데이트 */
        BigDecimal updatedFreeCredit =
            creditMapper.getFreeCredit(playerId).subtract(totalAccount);
        BigDecimal updatedPaidCredit = creditMapper.getPaidCredit(playerId);

        /* 유상 재화 업데이트 */
        if (updatedFreeCredit.compareTo(BigDecimal.ZERO) < 0) {
            updatedPaidCredit = updatedPaidCredit.add(updatedFreeCredit);
            updatedFreeCredit = BigDecimal.ZERO;
        }

        return new PlayerCredit(playerId, updatedPaidCredit,
            updatedFreeCredit);
    }
}