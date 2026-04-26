package az.cargora.cargora.controller;

import az.cargora.cargora.dto.request.UpdateDestinationBranch;
import az.cargora.cargora.dto.request.UpdateWeightRequest;
import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.dto.response.PackageResponse;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.service.PackageService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
@Validated
public class PackageController {

    private final PackageService packageService;

    @PostMapping("/create-new")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> createPackage(@RequestBody @Valid newPackageRequest request) {
        packageService.createPackage(request);
        return ResponseEntity.ok("Package created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageResponse> getPackage(@PathVariable Long id) {
        PackageResponse foundPackage = packageService.getPackageById(id);
        return ResponseEntity.ok(foundPackage);
    }

    // permitAll
    @GetMapping("/tracking-code/{code}")
    public ResponseEntity<PackageResponse> getPackageByTrackingCode(@PathVariable String code) {
        PackageResponse pkg = packageService.findByTrackingCode(code);
        return ResponseEntity.ok(pkg);
    }

    // this 2 methods still need update(for employye/admin and user other type)
    @GetMapping("/of-user/{PIN}")
    public ResponseEntity<List<PackageResponse>> getAllPackages(@PathVariable String PIN) {
        List<PackageResponse> packages = packageService.getUserPackages(PIN);
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PackageResponse>> getPackagesByStatus(@PathVariable PackageStatus status) {
        List<PackageResponse> packages = packageService.getPackagesByStatus(status);
        return ResponseEntity.ok(packages);
    }

    @PatchMapping("update-weight")
    public ResponseEntity<String> updateWeight(@Valid @RequestBody UpdateWeightRequest request) {
        packageService.updateWeight(request.getPackageId(), request.getWeight());
        return ResponseEntity.ok("Weight updated successfully");
    }

    @PatchMapping("update-destinationBranch")
    public ResponseEntity<String> updateDestinationBranch(@RequestBody @Valid UpdateDestinationBranch request) {
        packageService.updatePickUpPoints(request.getPackageId(), request.getDestinationBranchId());
        return ResponseEntity.ok("PickUpPoint updated successfully");
    }

    @GetMapping("filter-by")
    public List<PackageResponse> filter(
            @RequestParam(required = false) String pin,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) PackageStatus status,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) BigDecimal minWeight,
            @RequestParam(required = false) BigDecimal maxWeight) {

        return packageService.filterPackages(pin, branchId, status, from, to, minWeight, maxWeight);
    }

}
