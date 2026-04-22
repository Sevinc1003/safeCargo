package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PackageHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, Long> {

    List<PackageHistory> findByRelatedPackage_Id(Long packageId);

    Optional<PackageHistory> findTopByPackageIdOrderByTimestampDesc(Long packageId);

    // Xüsusi Query: Verilmiş tarixdən əvvəl baş vermiş bütün paket tarixçələrini tapır
    @org.springframework.data.jpa.repository.Query("SELECT ph FROM PackageHistory ph WHERE ph.timestamp < :date")
    java.util.List<PackageHistory> findHistoriesBeforeDate(@org.springframework.data.repository.query.Param("date") java.time.LocalDateTime date);

    // Nümunə Query: Müəyyən statusa sahib olan bütün tarixçələri tapmaq
    @org.springframework.data.jpa.repository.Query("SELECT ph FROM PackageHistory ph WHERE ph.status = :status")
    java.util.List<PackageHistory> findAllByStatus(@org.springframework.data.repository.query.Param("status") az.cargora.cargora.enums.PackageStatus status);

}
