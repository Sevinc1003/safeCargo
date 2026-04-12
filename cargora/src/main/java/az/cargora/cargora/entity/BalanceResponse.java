package az.cargora.cargora.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceResponse {
    private Long userId;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal bonus;
}
