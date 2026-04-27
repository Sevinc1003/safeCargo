package az.cargora.cargora.dto.response;

import az.cargora.cargora.enums.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PackageHistoryResponseDTO {

    private PackageStatus status;
    private LocalDateTime timestamp;

}