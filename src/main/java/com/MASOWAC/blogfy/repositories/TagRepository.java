package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);
}
