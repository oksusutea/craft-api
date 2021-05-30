package naverz.craft.craftapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.mapper.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("기존에 없는 아이템일경우, DB에 insert된다.")
    void insert_new_item() {
        //given
        PlayerInventory playerInventoryRequest = new PlayerInventory(1L, 3L, 3);

        //when
        orderService.purchaseItem(playerInventoryRequest);

        //then
        int quantity = orderMapper.getItemQuantity(playerInventoryRequest.getPlayerId(),
            playerInventoryRequest.getItemId());
        assertThat(quantity).isEqualTo(playerInventoryRequest.getQuantity());
    }

    @Test
    @DisplayName("기존에 있는 아이템일경우, DB에 update된다.")
    void update_exist_item() {
        //given
        PlayerInventory playerInventoryRequest = new PlayerInventory(1L, 1L, 3);

        //when
        int existQuantity = orderMapper.getItemQuantity(playerInventoryRequest.getPlayerId(),
            playerInventoryRequest.getItemId());
        orderService.purchaseItem(playerInventoryRequest);

        //then
        int quantity = orderMapper.getItemQuantity(playerInventoryRequest.getPlayerId(),
            playerInventoryRequest.getItemId());
        assertThat(quantity).isEqualTo(playerInventoryRequest.getQuantity() + existQuantity);

    }

}