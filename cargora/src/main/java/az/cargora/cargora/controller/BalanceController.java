package az.cargora.cargora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.cargora.cargora.dto.TopUpRequest;
import az.cargora.cargora.entity.Balance;
import az.cargora.cargora.service.BalanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;


    @PostMapping("/top-up")
    public ResponseEntity<Balance> topUpBalance(@RequestBody TopUpRequest request) {

        Balance balance = balanceService.topUpBalance(request);

        return ResponseEntity.ok(balance);
    }

}
