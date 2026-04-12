package az.cargora.cargora.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddBalanceRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Amount cannot be null" )
    @Positive(message = "Amount must be grater than zero")
    private BigDecimal amount;
}
