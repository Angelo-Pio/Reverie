package com.sapienza.reverie.Repository;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u.created_charms from User u where u.id = :userId")
    Optional<List<Charm>> findAllCreated(@Param("userId")  Long userId);

    @Query("select u.collected_charms from User u where u.id = :userId")
    Optional<List<Charm>> findAllCollected(@Param("userId")  Long userId);
}
