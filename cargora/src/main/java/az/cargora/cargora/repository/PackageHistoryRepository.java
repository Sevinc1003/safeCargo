package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PackageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, Long> {
}
