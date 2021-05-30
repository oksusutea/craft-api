package naverz.craft.craftapi.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.mapper.CreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CreditService {

    @Autowired
    CreditMapper creditMapper;

    @Transactional
    public BigDecimal chargeCredit(PlayerCredit creditRequest) {
        long playerId = creditRequest.getPlayerId();
        BigDecimal updatedFreeCredit =
            creditMapper.getFreeCredit(playerId).add(creditRequest.getFreeCredit());
        BigDecimal updatedPaidCredit =
            creditMapper.getPaidCredit(playerId).add(creditRequest.getPaidCredit());
        PlayerCredit updatedPlayer = PlayerCredit.builder()
            .playerId(playerId)
            .freeCredit(updatedFreeCredit)
            .paidCredit(updatedPaidCredit)
            .build();

        updateCredit(updatedPlayer);
        return updatedFreeCredit.add(updatedPaidCredit);
    }

    @Transactional
    public void updateCredit(PlayerCredit playerCredit) {
        creditMapper.updateCredit(playerCredit);
    }

}