package az.cargora.cargora.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWeightRequest {

    @NotNull(message = "please select your package")
    private Long packageId;
    @NotNull(message = "please enter weight")
    private BigDecimal weight;

}
