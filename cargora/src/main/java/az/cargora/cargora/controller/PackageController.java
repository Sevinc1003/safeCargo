package az.cargora.cargora.controller;


import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.service.PackageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PackageController {
    private final PackageService packageService;

    //BUNA REQUEST DTO EDERIK. DTO ICINDE VALIDASIYA, 
    // PARAMETR UCUN @Valid, class ucun @Validated
    @PostMapping("/packages")
    public ResponseEntity<Package> createPackage(@RequestBody Package pkg) {
        Package createdPackage = packageService.createPackage(pkg);
        return ResponseEntity.ok(createdPackage);
    }

    @GetMapping("/packages/{id}")
    public ResponseEntity<Package> getPackage(@PathVariable Long id) {
        Package foundPackage = packageService.getPackageById(id);
        return ResponseEntity.ok(foundPackage);
    }

    @GetMapping("/users/{userId}/packages")
    public ResponseEntity<Page<Package>> getAllPackages(@PathVariable Long userId, Pageable pageable) {
        Page<Package> packages = packageService.getUserPackages(userId, pageable);
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/packages/status/{status}")
    public ResponseEntity<Page<Package>> getPackagesByStatus(@PathVariable PackageStatus status, Pageable pageable) {
        Page<Package> packages = packageService.getPackagesByStatus(status,pageable);
        return ResponseEntity.ok(packages);
    }

    @PatchMapping("/package/{id}/weight")
    public ResponseEntity<Package> UpdateWeight(@PathVariable Long id,@RequestBody BigDecimal weight) {
        Package updatedPackage = packageService.updateWeight(id, weight);
        return ResponseEntity.ok(updatedPackage);
    }
    @PatchMapping("/package/{id}/destiantionBracnh")
    public ResponseEntity<Package> UpdateDestinationBranch(@PathVariable Long id,@RequestBody PickUpPoint destinationBranch) {
        Package updatePackage = packageService.updatePickUpPoints(id,destinationBranch);
        return ResponseEntity.ok(updatePackage);
    }

}
