package az.cargora.cargora.entity;
import jakarta.persistence.PostLoad;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayFeeRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Payment amount cannot be null")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal amount;
}
