package naverz.craft.craftapi.domain;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Item {

    @NotNull
    private Long id;
    @DecimalMin(value = "0.0")
    private BigDecimal price;
}
