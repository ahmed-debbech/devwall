package com.debbech.devwall.logic.feed;

import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.feed.PostTag;

import java.util.Set;

public interface IPostTagService {

    Set<PostTag> prepareTags(String tags, Post p);

}
