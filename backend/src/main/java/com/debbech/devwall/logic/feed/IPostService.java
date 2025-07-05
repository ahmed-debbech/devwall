package com.debbech.devwall.logic.feed;

import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.search.SearchedPost;

import java.util.List;

public interface IPostService {

    List<Post> getAllPaginated(int page_number);

    Post getSinglePostByRandomId(String randomid);

    List<SearchedPost> searchTermAndPaginate(String term, int page_number);
}
