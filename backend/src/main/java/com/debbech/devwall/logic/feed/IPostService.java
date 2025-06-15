package com.debbech.devwall.logic.feed;

import com.debbech.devwall.model.feed.Post;

import java.util.List;

public interface IPostService {

    void generateNewPost();

    void flushToDb();

    List<Post> getAllPaginated(int page_number);

    Post getSinglePostByRandomId(String randomid);

    List<Post> searchTermAndPaginate(String term, int page_number);
}
