package az.cargora.cargora.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    //     ADD MONEY
    public BalanceResponse addFounds(AddBalanceRequest request) {
        Balance balance = balanceRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Balance not found for User ID: " + request.getUserId()));

        BigDecimal newAmount = balance.getAmount().add(request.getAmount());
        balance.setAmount(newAmount);

        Balance savedBalance = balanceRepository.save(balance);

        return BalanceResponse.builder()
                .userId(savedBalance.getUser().getUserId())
                .amount(savedBalance.getAmount())
                .currency(savedBalance.getCurrency())
                .bonus(savedBalance.getBonus())
                .build();
    }

    //  DEDUCT MONEY (PAY SHIPPING FEE)
    public BalanceResponse payFee(PayFeeRequest request) {
        Balance balance = balanceRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Balance not found for User ID: " + request.getUserId()));

        if (balance.getAmount().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance! You need more funds.");
        }

        BigDecimal newAmount = balance.getAmount().subtract(request.getAmount());
        balance.setAmount(newAmount);

        Balance savedBalance = balanceRepository.save(balance);

        return BalanceResponse.builder()
                .userId(savedBalance.getUser().getUserId())
                .amount(savedBalance.getAmount())
                .currency(savedBalance.getCurrency())
                .bonus(savedBalance.getBonus())
                .build();
    }

    //ADD BONUS (CASHBACK)
    public void addBonus(Long userId, BigDecimal bonusAmount) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Balance not found for User ID: " + userId));

        BigDecimal currentBonus = balance.getBonus() != null ? balance.getBonus() : BigDecimal.ZERO;

        balance.setBonus(currentBonus.add(bonusAmount));
        balanceRepository.save(balance);
    }
}