package naverz.craft.craftapi.service;

import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.domain.PlayerCredit;

/* 재화 소진 정책 */
public interface CreditPolicy {

    PlayerCredit purchaseItem(PlayerInventory playerInventoryRequest);

}