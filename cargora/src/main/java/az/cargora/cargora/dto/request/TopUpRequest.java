package az.cargora.cargora.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TopUpRequest {

    private Long userId;
    private BigDecimal amount;

    //card number duzgunluyunu yoxlayan annotasiya(custom ederik extra point)
    private String cardNumber;

    //3 reqemli olmasini yoxlasin
    private String cvv;
}

