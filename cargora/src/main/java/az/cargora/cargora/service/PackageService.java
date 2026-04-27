
package az.cargora.cargora.service;

import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.dto.response.PackageResponse;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.Account;
import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.enums.UserRole;
import az.cargora.cargora.exception.customExceptions.UserNotFoundException;
import az.cargora.cargora.repository.AccountRepository;
import az.cargora.cargora.repository.PackageRepository;
import az.cargora.cargora.repository.PickUpPointRepository;
import az.cargora.cargora.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PackageService {

    private static final BigDecimal PRICE_PER_KG = BigDecimal.valueOf(5);

    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final PickUpPointRepository pickUpPointRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void createPackage(newPackageRequest request) {

        if (packageRepository.existsByTrackingNumber(request.getTrackingNumber())) {
            throw new RuntimeException("Package already exist");
        }

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
    public List<PackageResponse> getUserPackages(String PIN) {

        List<Package> pkgs = packageRepository.findByUserPIN(PIN);
        List<PackageResponse> pkgResponse = new ArrayList<>();
        ;

        for (Package p : pkgs) {

            pkgResponse.add(mapOf(p));

        }

        return pkgResponse;
    }

    @Transactional(readOnly = true)
    public PackageResponse getPackageById(Long packageId) {
        return mapOf(getById(packageId));
    }

    public PackageResponse findByTrackingCode(String code) {

        Package pkg = packageRepository.findByTrackingNumber(code)
                .or(() -> packageRepository.findByInternalTrackingCode(code))
                .orElseThrow(() -> new RuntimeException("Package not found with code: " + code));

        return mapOf(pkg);
    }

    @Transactional(readOnly = true)
    public List<PackageResponse> getPackagesByStatus(PackageStatus status) {

        List<Package> pkgs = packageRepository.findByCurrentStatus(status);
        List<PackageResponse> pkgResponse = new ArrayList<>();

        for (Package package1 : pkgs) {

            pkgResponse.add(mapOf(package1));

        }

        return pkgResponse;
    }

    @Transactional
    public void updateWeight(Long packageId, BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }

        Package pkg = getById(packageId);
        pkg.setWeight(weight);
        pkg.setShippingFee(calculateShippingFee(weight));
        packageRepository.save(pkg);
    }

    @Transactional
    public void updatePickUpPoints(Long packageId, Long destinationBranchId) {
        Package pkg = getById(packageId);
        PickUpPoint destionation = pickUpPointRepository.findById(destinationBranchId).get();
        pkg.setDestinationBranch(destionation);
        packageRepository.save(pkg);
    }

    @Transactional(readOnly = true)
    public List<PackageResponse> filterPackages(
            String pin,
            Long branchId,
            PackageStatus status,
            LocalDateTime from,
            LocalDateTime to,
            BigDecimal minWeight,
            BigDecimal maxWeight) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = getRoleByUsername(username);

        if(role.equals(UserRole.ROLE_USER.name())){

            pin = accountRepository.findByUsername(username).get().getUser().getPIN();
        }
        
        List<Package> pkgs = packageRepository.filterPackages(
                pin,
                branchId,
                status,
                from,
                to,
                minWeight,
                maxWeight);

        return pkgs.stream().map(this::mapOf).toList();
    }

    // ----------------------------------------

    private String getRoleByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());
        return account.getRole().name();
    }

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

    private PackageResponse mapOf(Package p) {

        HashMap<LocalDateTime, PackageStatus> statuses = new HashMap<>();
        for (PackageHistory ph : p.getHistory()) {

            statuses.put(ph.getTimestamp(), ph.getStatus());
            System.out.println(ph.getStatus().name());
        }

        return new PackageResponse(p.getUser().getPIN(),
                p.getDestinationBranch().getName(),
                p.getTrackingNumber(),
                p.getInternalTrackingCode(),
                p.getWeight(),
                p.getShippingFee(),
                p.getCreatedAt(),
                statuses);

    }
}