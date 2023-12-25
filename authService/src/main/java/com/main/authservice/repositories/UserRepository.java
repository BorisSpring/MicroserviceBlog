package com.main.authservice.repositories;

import com.main.authservice.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    @Query("SELECT u FROM User u WHERE (:filter IS NULL or u.enabled =:filter)")
    Page<User>  findAll(@Nullable @Param("filter") Boolean filter, PageRequest pageRequest);

    boolean existsByEmail(String email);

    List<User> findByIdIn(List<Integer> userIds);
}
