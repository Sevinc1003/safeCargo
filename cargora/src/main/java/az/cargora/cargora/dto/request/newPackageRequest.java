package az.cargora.cargora.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class newPackageRequest {


    @NotNull
    private Long userId;

    @NotNull
    private Long destinationBranchId;

    @NotBlank
    private String trackingNumber;

    @NotNull
    private BigDecimal weight;
}

    
