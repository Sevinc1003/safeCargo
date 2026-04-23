package az.cargora.cargora.controller;


import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.service.PackageService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

@PostMapping("/create-new")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public ResponseEntity<?> createPackage(@RequestBody @Valid newPackageRequest request) {
    packageService.createPackage(request);
    return ResponseEntity.ok("Package created");
}

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackage(@PathVariable Long id) {
        Package foundPackage = packageService.getPackageById(id);
        return ResponseEntity.ok(foundPackage);
    }

    @GetMapping("/of-user/{userId}")
    public ResponseEntity<List<Package>> getAllPackages(@PathVariable Long userId) {
        List<Package> packages = packageService.getUserPackages(userId);
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/packages/status/{status}")
    public ResponseEntity<List<Package>> getPackagesByStatus(@PathVariable PackageStatus status) {
        List<Package> packages = packageService.getPackagesByStatus(status);
        return ResponseEntity.ok(packages);
    }

    @PatchMapping("package/{id}/weight")
    public ResponseEntity<Package> UpdateWeight(@PathVariable Long id,@RequestBody BigDecimal weight) {
        Package updatedPackage = packageService.updateWeight(id, weight);
        return ResponseEntity.ok(updatedPackage);
    }
    @PatchMapping("package/{id}/destiantionBracnh")
    public ResponseEntity<Package> UpdateDestinationBranch(@PathVariable Long id,@RequestBody PickUpPoint destiantionBranch) {
        Package updatePackage = packageService.updatePickUpPoints(id,destiantionBranch);
        return ResponseEntity.ok(updatePackage);
    }

}
