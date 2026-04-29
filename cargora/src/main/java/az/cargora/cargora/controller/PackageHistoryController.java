package az.cargora.cargora.controller;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.service.PackageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/package-history")
@RequiredArgsConstructor
public class PackageHistoryController {

    private final PackageHistoryService packageHistoryService;

    @GetMapping("/{packageId}")
    public ResponseEntity<Page<PackageHistoryResponseDTO>> getPackageHistory(
            @PathVariable Long packageId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<PackageHistoryResponseDTO> history =
                packageHistoryService.getPackageHistory(packageId, pageable);
        return ResponseEntity.ok(history);
    }
}