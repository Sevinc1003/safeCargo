package az.cargora.cargora.controller;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.dto.response.PageResponse;
import az.cargora.cargora.service.PackageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("package-history")
@RequiredArgsConstructor
public class PackageHistoryController {

    private final PackageHistoryService packageHistoryService;

    @GetMapping("/{packageId}")
    public ResponseEntity<PageResponse<PackageHistoryResponseDTO>> getPackageHistory(
            @PathVariable Long packageId,
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                packageHistoryService.getPackageHistory(packageId, pageable));
    }
}