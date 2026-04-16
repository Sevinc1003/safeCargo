package az.cargora.cargora.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class FakeStripeService {

    public StripeChargeResponse charge(StripeChargeRequest request) {

        StripeChargeResponse response = new StripeChargeResponse();

        //success is false
        if (request.getCardNumber() == null || request.getCardNumber().length() < 8) {
            response.setSuccess(false);
            response.setMessage("Invalid card number");
            return response;
        }

        //invalid amount
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            response.setSuccess(false);
            response.setMessage("Invalid amount");
            return response;
        }

        //success is true
        response.setSuccess(true);
        response.setTransactionId(UUID.randomUUID().toString());
        response.setMessage("Payment successful");

        return response;
    }
}

