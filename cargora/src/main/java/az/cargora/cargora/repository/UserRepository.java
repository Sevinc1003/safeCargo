package az.cargora.cargora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import az.cargora.cargora.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByPIN(String PIN);

    // Xüsusi Query: Qeydiyyatda olan adlarına (first name) əsasən istifadəçiləri tapır
    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u WHERE u.fullname.name = :firstName")
    java.util.List<User> findUsersByFirstName(@org.springframework.data.repository.query.Param("firstName") String firstName);

    // Nümunə Query: Balansı müəyyən məbləğdən böyük olan istifadəçiləri tapmaq
    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u WHERE u.balance > :minBalance")
    java.util.List<User> findUsersWithMinimumBalance(@org.springframework.data.repository.query.Param("minBalance") java.math.BigDecimal minBalance);








}
