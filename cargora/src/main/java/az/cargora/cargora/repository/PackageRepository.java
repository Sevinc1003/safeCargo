package az.cargora.cargora.repository;

import az.cargora.cargora.entity.Package;
import az.cargora.cargora.enums.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Page<Package> findByUserUserId(Long userId, Pageable pageable);

    Page<Package> findByUserPIN(String pin, Pageable pageable );

    boolean existsByTrackingNumber(String trackingNumber);

    @Query("""
            select p
            from Package p
            join p.history h
            where h.status = :status
              and h.timestamp = (
                  select max(h2.timestamp)
                  from PackageHistory h2
                  where h2.relatedPackage = p
              )
            """)
    List<Package> findByCurrentStatus(@Param("status") PackageStatus status);

    Optional<Package> findByTrackingNumber(String trackingNumber);

    Optional<Package> findByInternalTrackingCode(String internalTrackingCode);

    @Query("""
                SELECT p
                FROM Package p
                JOIN PackageHistory ph ON ph.relatedPackage = p
                WHERE
                    (:pin IS NULL OR p.user.PIN = :pin)
                    AND (:branchId IS NULL OR p.destinationBranch.id = :branchId)
                    AND (
                        :status IS NULL OR (
                            ph.timestamp = (
                                SELECT MAX(ph2.timestamp)
                                FROM PackageHistory ph2
                                WHERE ph2.relatedPackage = p
                            )
                            AND ph.status = :status
                        )
                    )
                    AND (:from IS NULL OR p.createdAt >= :from)
                    AND (:to IS NULL OR p.createdAt <= :to)
                    AND (:minWeight IS NULL OR p.weight >= :minWeight)
                    AND (:maxWeight IS NULL OR p.weight <= :maxWeight)
            """)
    List<Package> filterPackages(
            @Param("pin") String pin,
            @Param("branchId") Long branchId,
            @Param("status") PackageStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("minWeight") BigDecimal minWeight,
            @Param("maxWeight") BigDecimal maxWeight);

}
