package az.cargora.cargora.repository;

import az.cargora.cargora.entity.PickUpPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PickUpPointRepository extends JpaRepository<PickUpPoint, Long> {

    Optional<PickUpPoint> findById(Long id);

    Optional<PickUpPoint> findByAddress(String address);
}