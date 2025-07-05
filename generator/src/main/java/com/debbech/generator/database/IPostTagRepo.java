package com.debbech.generator.database;

import com.debbech.generator.model.feed.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPostTagRepo extends JpaRepository<PostTag, Long> {
    Optional<PostTag> findByName(String trim);

}
