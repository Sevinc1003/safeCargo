package az.cargora.cargora.controller;


import az.cargora.cargora.entity.Package;
import az.cargora.cargora.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<List<Package>> getAllPackages(@PathVariable Long userId) {
        List<Package> packages = packageService.getUserPackages(userId);
        return ResponseEntity.ok(packages);
    }
}
