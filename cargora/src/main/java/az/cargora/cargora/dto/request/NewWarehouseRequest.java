package az.cargora.cargora.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewWarehouseRequest {

    @NotBlank
    private String country;

    @NotBlank
    private String addressTitle;

    @NotBlank
    private String addressDetails;

    @NotBlank
    private String ZIPcode;

    @NotBlank
    private String phoneNumber;
}
