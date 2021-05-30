package naverz.craft.craftapi.controller;

import java.math.BigDecimal;
import javax.validation.Valid;
import naverz.craft.craftapi.domain.PlayerCredit;
import naverz.craft.craftapi.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    @Autowired
    CreditService creditService;

    @PostMapping("/charge")
    public ResponseEntity<BigDecimal> chargeCredit(
        @RequestBody @Valid PlayerCredit creditRequest) {
        BigDecimal updatedCredit = creditService.chargeCredit(creditRequest);

        return ResponseEntity.ok(updatedCredit);
    }

}