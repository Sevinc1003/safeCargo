package az.cargora.cargora.repository;


import az.cargora.cargora.entity.Package;
import az.cargora.cargora.enums.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {

    //bu query gedib useer cedvelinden melumati cekir
    Page<Package> findByUserUserId(Long userId, Pageable pageable);

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

    Page<Package> findByCurrentStatus(@Param("status") PackageStatus status, Pageable pageable);

    Optional<Package> findByTrackingNumber(String trackingNumber);

    // Xüsusi Query: Müəyyən bir təhvil məntəqəsinə (filiala) göndərilmiş bütün bağlamaları tapır
    @Query("SELECT p FROM Package p WHERE p.destinationBranch.id = :branchId")
    java.util.List<Package> findPackagesByDestinationBranch(@Param("branchId") Long branchId);

}
