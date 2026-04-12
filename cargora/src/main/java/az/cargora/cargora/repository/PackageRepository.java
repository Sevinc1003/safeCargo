package az.cargora.cargora.repository;


import az.cargora.cargora.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {

    List<Package> findByUserId(Long userId);


    Optional<Package> findByTrackingNumber(String trackingNumber);


}
