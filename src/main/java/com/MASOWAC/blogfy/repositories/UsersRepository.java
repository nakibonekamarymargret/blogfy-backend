package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
  boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM Users u WHERE (u.username = :input OR u.email = :input) AND u.password = :password")
    Users login(@Param("input") String input, @Param("password") String password);

    @Query("SELECT u FROM Users u WHERE (u.username = :loginDetails OR u.email = :loginDetails)")
    Optional<Users> findByLoginDetails(@Param("loginDetails") String loginDetails);

  

    @Query("SELECT u.name AS name, u.username AS username, u.email AS email, u.profilePicUrl AS profilePicUrl FROM Users u WHERE u.username = :username")
    Optional<ProfileView> getUserProfile(@Param("username") String username);

}
