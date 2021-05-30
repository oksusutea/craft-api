package naverz.craft.craftapi.domain;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerCredit {

    @NotNull
    private Long playerId;
    @DecimalMin(value = "0.0")
    private BigDecimal paidCredit;
    @DecimalMin(value = "0.0")
    private BigDecimal freeCredit;

}
