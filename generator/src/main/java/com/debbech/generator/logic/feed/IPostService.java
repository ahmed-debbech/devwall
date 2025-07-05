package com.debbech.generator.logic.feed;

import com.debbech.generator.model.feed.Post;

import java.util.List;

public interface IPostService {

    void generateNewPost();

    void flushToDb();

}
