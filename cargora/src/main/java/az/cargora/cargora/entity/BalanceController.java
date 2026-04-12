package az.cargora.cargora.entity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMaping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/add")
    public ResponseEntity<BalanceResponse> addFunds(@Valid @RequestBody AddBalanceRequest request) {
        BalanceResponse response = balanceService.addFounds(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/pay")
    public ResponseEntity<BalanceResponse> payFee(@Valid @RequestBody PayFeeRequest request){
        BalanceResponse response =balanceService.payFee(request);
        return ResponseEntity.ok(response);
    }
}
