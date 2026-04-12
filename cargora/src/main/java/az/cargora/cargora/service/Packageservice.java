package az.cargora.cargora.service;

import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.PackageStatus;
import az.cargora.cargora.repository.PackageHistoryRepository;
import az.cargora.cargora.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final PackageHistoryRepository packageHistoryRepository;

    @Transactional
    public Package createPackage(Package pkg) {
        pkg.setInternalTrackingCode(generateInternalCode());
        pkg.setShippingFee(BigDecimal.ZERO);

        Package saved = packageRepository.save(pkg);
        addHistory(saved, PackageStatus.DECLARED);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Package> getUserPackages(Long userId) {
        return packageRepository.findByUser_UserId(userId);
    }

    @Transactional
    public Package updateWeight(Long packageId, BigDecimal weight) {
        Package pkg = getById(packageId);
        pkg.setWeight(weight);
        pkg.setShippingFee(calculateShippingFee(weight));
        return packageRepository.save(pkg);
    }
    private void addHistory(Package pkg, PackageStatus status) {
        PackageHistory entry = new PackageHistory(status, LocalDateTime.now());
        entry.setRelatedPackage(pkg);
        packageHistoryRepository.save(entry);
    }
    private BigDecimal calculateShippingFee(BigDecimal weight) {
        if (weight == null) {
            return BigDecimal.ZERO;
        }
        return weight.multiply(BigDecimal.valueOf(5));
    }

    private String generateInternalCode() {
        return "EXP-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Package getById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}
