package naverz.craft.craftapi.mapper;

import naverz.craft.craftapi.domain.PlayerInventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Integer getItemQuantity(long playerId, long itemId);

    void purchaseItem(PlayerInventory playerInventoryRequest);

    void insertItem(PlayerInventory playerInventoryRequest);

    void UpdateItemQuantity(PlayerInventory playerInventoryRequest);
}
