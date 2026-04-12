package az.cargora.cargora.payment;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StripeChargeRequest {

    private String cardNumber;
    private String cvv;
    private BigDecimal amount;
    private String currency;

}
