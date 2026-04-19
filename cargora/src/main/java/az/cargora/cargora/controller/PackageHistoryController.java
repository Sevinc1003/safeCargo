package az.cargora.cargora.controller;


import az.cargora.cargora.service.PackageHistoryService;
import az.cargora.cargora.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageHistoryController {

    private final PackageHistoryService historyService;

    @GetMapping("/{packageId}/history")
    public ResponseEntity<?> getHistory(@PathVariable Long packageId) {
            return ResponseEntity.ok(historyService.getPackageHistory(packageId));

    }
}
