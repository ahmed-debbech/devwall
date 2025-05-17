package com.debbech.devwall.database;

import com.debbech.devwall.model.feed.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.status = 'DONE'")
    List<Post> getAllDonePosts(Pageable pageable);

}
