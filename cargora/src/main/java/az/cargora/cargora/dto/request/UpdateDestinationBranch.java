package az.cargora.cargora.dto.request;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import az.cargora.cargora.entity.PickUpPoint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDestinationBranch {

    @NotNull(message = "please select your package")
    private Long packageId;
    
    @NotNull(message = "please select new pick-up point")
    private Long destinationBranchId;

}
