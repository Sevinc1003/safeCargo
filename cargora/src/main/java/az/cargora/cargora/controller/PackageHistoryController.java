package az.cargora.cargora.controller;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.service.PackageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/package-history")
@RequiredArgsConstructor
public class PackageHistoryController {

    private final PackageHistoryService packageHistoryService;

    @GetMapping("/{packageId}")
    public List<PackageHistoryResponseDTO> getPackageHistory(@PathVariable Long packageId) {
        return packageHistoryService.getPackageHistory(packageId);
    }
}