package az.cargora.cargora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import az.cargora.cargora.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByPIN(String PIN);

    boolean existsByEmail(String email);


    // 1. Template to bring all users in a paginated list
    @Query("SELECT u FROM User u")
    Page<User> findAll(Pageable pageable);

    // 2. Template to find a specific user quickly using their email address
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    Page<User> findByEmailContaining(@Param("email") String email, Pageable pageable);

    // 3. Template to search for a user by matching the search term against both their first name AND surname.
    @Query("SELECT u FROM User u WHERE u.fullname.name LIKE %:searchTerm% OR u.fullname.surname LIKE %:searchTerm%")
    Page<User> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

}
