package az.cargora.cargora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import az.cargora.cargora.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import az.cargora.cargora.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByUsername(String username); // email login üçün

    Optional<Account> findByUser_UserId(Long userId);

    boolean existsByUsername(String username);

    // Template filters accounts by their role to help manage administrative and employee access.
    @Query("SELECT a FROM Account a WHERE a.role = :role")
    Page<Account> findByRole(UserRole role, Pageable pageable);

}


