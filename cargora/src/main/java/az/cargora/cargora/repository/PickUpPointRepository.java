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

    // Xüsusi Query: Dəqiq telefon nömrəsinə görə təhvil məntəqəsini axtarır
    @org.springframework.data.jpa.repository.Query("SELECT p FROM PickUpPoint p WHERE p.phoneNumber = :phone")
    java.util.Optional<PickUpPoint> findByPhoneNumber(@org.springframework.data.repository.query.Param("phone") String phone);

    // Nümunə Query: Adında müəyyən söz olan təhvil məntəqələrini tapmaq
    @org.springframework.data.jpa.repository.Query("SELECT p FROM PickUpPoint p WHERE p.name LIKE %:keyword%")
    java.util.List<PickUpPoint> searchByName(@org.springframework.data.repository.query.Param("keyword") String keyword);

}