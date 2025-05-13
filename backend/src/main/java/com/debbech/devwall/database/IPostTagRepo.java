package com.debbech.devwall.database;

import com.debbech.devwall.model.feed.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostTagRepo extends JpaRepository<PostTag, String> {
}
