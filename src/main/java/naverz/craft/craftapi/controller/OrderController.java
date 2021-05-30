package naverz.craft.craftapi.controller;

import javax.validation.Valid;
import naverz.craft.craftapi.domain.PlayerInventory;
import naverz.craft.craftapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/purchase")
    public ResponseEntity<Enum<HttpStatus>> purchaseItem(
        @RequestBody @Valid PlayerInventory playerInventoryRequest) {
        orderService.purchaseItem(playerInventoryRequest);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}