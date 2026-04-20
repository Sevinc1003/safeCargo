package az.cargora.cargora.service;

import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.repository.PackageRepository;
import az.cargora.cargora.repository.PickUpPointRepository;
import az.cargora.cargora.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PickUpPointRepository pickUpPointRepository;

    @Transactional
    public void createPackage(newPackageRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PickUpPoint branch = pickUpPointRepository.findById(request.getDestinationBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        Package pkg = new Package();

        pkg.setUser(user);
        pkg.setDestinationBranch(branch);
        pkg.setTrackingNumber(request.getTrackingNumber());
        pkg.setWeight(request.getWeight()); 
        pkg.setShippingFee(BigDecimal.ZERO);
        pkg.setInternalTrackingCode(generateInternalTrackingCode());

        packageRepository.save(pkg);
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
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }

        Package pkg = getById(packageId);
        pkg.setWeight(weight);
        pkg.setShippingFee(calculateShippingFee(weight));
        return packageRepository.save(pkg);
    }

    @Transactional
    public Package updatePickUpPoints(Long packageId, PickUpPoint destinationBranch) {
        Package pkg = getById(packageId);
        pkg.setDestinationBranch(destinationBranch);
        return packageRepository.save(pkg);
    }

    
//----------------------------------------
    @Transactional
    private BigDecimal calculateShippingFee(BigDecimal weight) {
        if (weight == null) {
            return BigDecimal.ZERO;
        }
        return weight.multiply(PRICE_PER_KG);
    }

    private String generateInternalTrackingCode() {
        return "EXP-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Package getById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}