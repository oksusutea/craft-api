package naverz.craft.craftapi.service;

import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private Map<String, CreditPolicy> creditPolicyMap;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CreditService creditService;

    @Transactional
    public void purchaseItem(PlayerInventory playerInventoryRequest) {
        long playerId = playerInventoryRequest.getPlayerId();

        final CreditPolicy creditPolicy = creditPolicyMap
            .get(getPurchasePolicy(playerId)); // 소진 정책 셋팅

        PlayerCredit updatedPlayerCredit = creditPolicy.purchaseItem(playerInventoryRequest);

        creditService.updateCredit(updatedPlayerCredit);    // 재화 차감

        updatePlayerInventory(playerInventoryRequest);
    }

    @Transactional
    public void updatePlayerInventory(PlayerInventory playerInventoryRequest) {
        long playerId = playerInventoryRequest.getPlayerId();
        long itemId = playerInventoryRequest.getItemId();

        // 인벤토리 추가(혹은 개수 증가)
        Optional<Integer> existQuantity = Optional
            .ofNullable(orderMapper.getItemQuantity(playerId, itemId));

        if (existQuantity.isEmpty() || existQuantity.get() == 0) {
            orderMapper.insertItem(playerInventoryRequest);
        } else {
            orderMapper.UpdateItemQuantity(playerInventoryRequest);
        }
    }

    public String getPurchasePolicy(long playerId) {
        return "fixedDefaultPolicy";   //TO-DO: 플레이어에 따라 다른 정책을 가질 수 있도록 메소드 추출
    }
}