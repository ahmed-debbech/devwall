package com.debbech.devwall.logic.feed;

import com.debbech.devwall.database.IPostRepo;
import com.debbech.devwall.database.IPostTagRepo;
import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.search.SearchedPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${ai.generate}")
    private String generate;

    @Autowired
    private IPostRepo postRepo;
    @Autowired
    private IPostTagRepo tagRepo;
    @Autowired
    private IPostTagService postTagService;


    @Override
    public List<Post> getAllPaginated(int page_number) {

        Pageable page = PageRequest.of(page_number, 5);
        List<Post> posts = postRepo.getAllDonePosts(page);
        return posts;
    }

    @Override
    public Post getSinglePostByRandomId(String randomid) {
        return postRepo.getSinglePostByRandomId(randomid).orElse(null);
    }

    @Override
    public List<SearchedPost> searchTermAndPaginate(String term, int page_number) {
        Pageable page = PageRequest.of(page_number, 5);
        List<SearchedPost> posts = postRepo.getAllDonePostsWithSearchTerm(term);
        return posts;
    }
}
