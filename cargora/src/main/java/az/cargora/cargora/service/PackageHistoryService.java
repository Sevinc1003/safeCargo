package az.cargora.cargora.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.PackageStatus;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.repository.PackageHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackageHistoryService {

    private final PackageHistoryRepository repo;
    private final UserService userService;

    private final BigDecimal DELIVERY_BONUS = new BigDecimal(2.5);

    public void newStatus(az.cargora.cargora.entity.Package pkg, PackageStatus status) {

        if (status == PackageStatus.DELIVERED) {

            User user = pkg.getUser();

            BigDecimal bonus = user.getBonus();
            
            userService.updateBonus(user.getUserId(), user.getBonus().add(DELIVERY_BONUS));
            
        }

       
        repo.save(new PackageHistory(pkg, status));

    }

    public List<PackageHistory> getPackageHistory(Long packageId) {
    return repo.findByRelatedPackage_Id(packageId);
}

}
