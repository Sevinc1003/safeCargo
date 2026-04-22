package az.cargora.cargora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import az.cargora.cargora.entity.Account;

public interface AccountRepository extends JpaRepository <Account , Long>{

    Optional<Account> findByUsername(String username); // email login üçün

    boolean existsByUsername(String username);

    // Xüsusi Query: Sistemdə müəyyən bir rola (məsələn: ADMIN) sahib bütün hesabları tapır
    @org.springframework.data.jpa.repository.Query("SELECT a FROM Account a WHERE a.role = :role")
    java.util.List<Account> findAllByRole(@org.springframework.data.repository.query.Param("role") az.cargora.cargora.enums.UserRole role);

    // Nümunə Query: Bütün aktiv hesabları tapmaq (Example: Find all active accounts)
    @org.springframework.data.jpa.repository.Query("SELECT a FROM Account a WHERE a.enabled = true")
    java.util.List<Account> findAllActiveAccounts();


}
