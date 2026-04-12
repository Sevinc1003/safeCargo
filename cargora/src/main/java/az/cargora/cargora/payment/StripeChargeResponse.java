package az.cargora.cargora.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripeChargeResponse {

    private boolean success;
    private String transactionId;
    private String message;

    

}
