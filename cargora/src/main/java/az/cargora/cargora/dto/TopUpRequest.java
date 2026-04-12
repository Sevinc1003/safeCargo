package az.cargora.cargora.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TopUpRequest {

    private Long userId;
    private BigDecimal amount;

    private String cardNumber;
    private String cvv;
}

