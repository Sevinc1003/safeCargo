package az.cargora.cargora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import az.cargora.cargora.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByUsername(String username); // email login üçün

    boolean existsByUsername(String username);

}
