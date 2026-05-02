package az.cargora.cargora.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.dto.response.PageResponse;
import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.repository.PackageHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PackageHistoryService {

    private final PackageHistoryRepository repo;
    private final UserService userService;

    private final BigDecimal DELIVERY_BONUS = new BigDecimal(2.5);

    public void newStatus(az.cargora.cargora.entity.Package pkg, PackageStatus newStatus) {

        if (newStatus == PackageStatus.DELIVERED) {

            User user = pkg.getUser();

            BigDecimal updatedBonus = user.getBonus().add(DELIVERY_BONUS);
            
            userService.updateBonus(user.getUserId(), updatedBonus);
            
        }

       
        repo.save(new PackageHistory(pkg, newStatus));

    }

@Transactional(readOnly = true)
public PageResponse<PackageHistoryResponseDTO> getPackageHistory(Long packageId, Pageable pageable) {

    Page<PackageHistoryResponseDTO> page = repo
            .findByRelatedPackageId(packageId, pageable)
            .map(history -> new PackageHistoryResponseDTO(
                    history.getStatus(),
                    history.getTimestamp()
            ));

    return PageResponse.from(page);
}
}

