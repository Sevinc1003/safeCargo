package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.dto.TopUpRequest;
import az.cargora.cargora.entity.Balance;
import az.cargora.cargora.payment.FakeStripeService;
import az.cargora.cargora.payment.StripeChargeRequest;
import az.cargora.cargora.payment.StripeChargeResponse;
import az.cargora.cargora.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final FakeStripeService fakeStripeService;

    public Balance topUpBalance(TopUpRequest request) {

        // 1. Stripe request
        StripeChargeRequest stripeRequest = new StripeChargeRequest(request.getCardNumber(), 
                                                                    request.getCvv(), 
                                                                    request.getAmount(), 
                                                                    "AZN");



        // 2. Payment
        StripeChargeResponse stripeResponse = fakeStripeService.charge(stripeRequest);

        // 3. if successed
        if (stripeResponse.isSuccess()) {

            Balance balance = balanceRepository
                    .findByUser(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Balance not found"));

            balance.setBalance(
                    balance.getBalance().add(request.getAmount())
            );

            balanceRepository.save(balance);

            return balance;

        } else {
            throw new RuntimeException(stripeResponse.getMessage());
        }
    }
}