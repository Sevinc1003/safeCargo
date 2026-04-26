package az.cargora.cargora.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.enums.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackageResponse {

    private String PIN;

    private String destinationBranch;

    private String trackingNumber;

    private String internalTrackingCode;

    private BigDecimal weight;

    private BigDecimal shippingFee;

    private LocalDateTime createdAt;

    private HashMap<LocalDateTime, PackageStatus> statuses;
}
