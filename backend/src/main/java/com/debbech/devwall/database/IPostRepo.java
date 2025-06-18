package com.debbech.devwall.database;

import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.search.SearchedPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.status = 'DONE'")
    List<Post> getAllDonePosts(Pageable pageable);

    @Query("select p from Post p where p.randomId = ?1")
    Optional<Post> getSinglePostByRandomId(String id);

    @Query("select p from Post p join p.tags t where p.status = 'DONE' and t.name = ?1")
    List<Post> getAllDonePostsWithTagName(Pageable k, String t);

    @Query(value = "select p.id, p.title, p.created_at, p.status, p.random_id, p.body from Post p where p.status = 'DONE' order by SIMILARITY(p.body, '?1') desc limit 50", nativeQuery = true)
    List<SearchedPost> getAllDonePostsWithSearchTerm(String term);
}
