package com.debbech.devwall.database;

import com.debbech.devwall.model.feed.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPostTagRepo extends JpaRepository<PostTag, Long> {
    Optional<PostTag> findByName(String trim);
}
