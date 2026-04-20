package az.cargora.cargora.controller;


import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PickUpPoint;
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

@PostMapping("/packages")
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

    @PatchMapping("/{id}/new-weight")
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public ResponseEntity<Package> UpdateWeight(@PathVariable Long id,@RequestBody BigDecimal newWeight) {
        Package updatedPackage = packageService.updateWeight(id, newWeight);
        return ResponseEntity.ok(updatedPackage);
    }

    @PatchMapping("{id}/new-destiantionBracnh")
    public ResponseEntity<Package> UpdateDestinationBranch(@PathVariable Long id,@RequestBody PickUpPoint newDestiantionBranch) {
        Package updatePackage = packageService.updatePickUpPoints(id,newDestiantionBranch);
        return ResponseEntity.ok(updatePackage);
    }

}
