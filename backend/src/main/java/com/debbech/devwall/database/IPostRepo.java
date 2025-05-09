package com.debbech.devwall.database;

import com.debbech.devwall.model.feed.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {
}
