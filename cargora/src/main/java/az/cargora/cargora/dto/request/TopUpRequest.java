package az.cargora.cargora.dto.request;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TopUpRequest {

    private Long userId;
    
    @NotBlank
    private BigDecimal amount;

    @NotBlank
    @CreditCardNumber
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "\\d{3}") //3 reqemli olmasini yoxlasin
    private String cvv;
}

