package az.cargora.cargora.repository;


import az.cargora.cargora.entity.Package;
import az.cargora.cargora.enums.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {

    List<Package> findByUserUserId(Long userId);

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


    

}
