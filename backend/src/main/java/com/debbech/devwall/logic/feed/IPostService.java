package com.debbech.devwall.logic.feed;

public interface IPostService {

    void generateNewPost();

    void flushToDb();
}
