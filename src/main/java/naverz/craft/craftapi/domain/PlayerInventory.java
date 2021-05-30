package naverz.craft.craftapi.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayerInventory {

    @NotNull
    private Long playerId;
    @NotNull
    private Long itemId;
    @Min(0)
    private int quantity;
}