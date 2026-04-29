package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PickUpPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PickUpPointRepository extends JpaRepository<PickUpPoint, Long> {

    Optional<PickUpPoint> findById(Long id);

    Optional<PickUpPoint> findByAddress(String address);

    // Template that allows users to find delivery points by searching for a city or region within the address string.
    @Query("SELECT p FROM PickUpPoint p WHERE p.address LIKE %:region%")
    Page<PickUpPoint> findByAddressContaining(@Param("region") String region, Pageable pageable);
}