package naverz.craft.craftapi.mapper;

import java.math.BigDecimal;
import naverz.craft.craftapi.domain.PlayerCredit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreditMapper {

    BigDecimal getFreeCredit(long playerId);

    BigDecimal getPaidCredit(long playerId);

    void updateCredit(PlayerCredit playerCredit);

}