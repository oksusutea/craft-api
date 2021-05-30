package naverz.craft.craftapi.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public BigDecimal getItemPrice(long itemId) {
        return BigDecimal.valueOf(50L);
    }

}