package az.cargora.cargora.service;

import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PackageStatus;
import az.cargora.cargora.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PackageService {

    private static final BigDecimal PRICE_PER_KG = BigDecimal.valueOf(5);

    private final PackageRepository packageRepository;
    private final PackageHistoryService packageHistory;

    @Transactional
    public Package createPackage(Package pkg) {
        pkg.setInternalTrackingCode(generateInternalCode());
        pkg.setShippingFee(BigDecimal.ZERO);

        Package saved = packageRepository.save(pkg);
        packageHistory.newStatus(saved, PackageStatus.DECLARED);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Package> getUserPackages(Long userId) {
        return packageRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public Package getPackageById(Long packageId) {
        return getById(packageId);
    }

    @Transactional
    public Package updateWeight(Long packageId, BigDecimal weight) {
        Package pkg = getById(packageId);
        pkg.setWeight(weight);
        pkg.setShippingFee(calculateShippingFee(weight));
        return packageRepository.save(pkg);
    }

    private BigDecimal calculateShippingFee(BigDecimal weight) {
        if (weight == null) {
            return BigDecimal.ZERO;
        }
        return weight.multiply(PRICE_PER_KG);
    }

    private String generateInternalCode() {
        return "EXP-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Package getById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}
package az.cargora.cargora.service;

import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PackageStatus;
import az.cargora.cargora.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PackageService {

    private static final BigDecimal PRICE_PER_KG = BigDecimal.valueOf(5);

    private final PackageRepository packageRepository;
    private final PackageHistoryService packageHistory;

    @Transactional
    public Package createPackage(Package pkg) {
        pkg.setInternalTrackingCode(generateInternalCode());
        pkg.setShippingFee(BigDecimal.ZERO);

        Package saved = packageRepository.save(pkg);
        packageHistory.newStatus(saved, PackageStatus.DECLARED);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Package> getUserPackages(Long userId) {
        return packageRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public Package getPackageById(Long packageId) {
        return getById(packageId);
    }

    @Transactional
    public Package updateWeight(Long packageId, BigDecimal weight) {
        Package pkg = getById(packageId);
        pkg.setWeight(weight);
        pkg.setShippingFee(calculateShippingFee(weight));
        return packageRepository.save(pkg);
    }

    private BigDecimal calculateShippingFee(BigDecimal weight) {
        if (weight == null) {
            return BigDecimal.ZERO;
        }
        return weight.multiply(PRICE_PER_KG);
    }

    private String generateInternalCode() {
        return "EXP-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Package getById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}
