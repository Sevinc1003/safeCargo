package az.cargora.cargora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import az.cargora.cargora.entity.Balance;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository <Balance, Long>{

    Optional<Balance> findByUserId(Long userId);


}