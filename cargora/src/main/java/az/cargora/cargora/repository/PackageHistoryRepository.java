package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PackageHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, Long> {

    List<PackageHistory> findByRelatedPackageId(Long packageId);

    Optional<PackageHistory> findTopByRelatedPackageIdOrderByTimestampDesc(Long packageId);
}
