package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.PackageStatus;
import az.cargora.cargora.repository.PackageHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackageHistoryService {

    private final PackageHistoryRepository repo;


    public void newStatus(az.cargora.cargora.entity.Package pkg, PackageStatus status) {

       
        repo.save(new PackageHistory(pkg, status));

    }

}
