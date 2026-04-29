package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PackageHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, Long> {

    Page<PackageHistory> findByRelatedPackageId(Long packageId, Pageable pageable);

    Optional<PackageHistory> findTopByRelatedPackageIdOrderByTimestampDesc(Long packageId);
}
