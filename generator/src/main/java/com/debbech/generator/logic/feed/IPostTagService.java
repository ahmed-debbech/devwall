package com.debbech.generator.logic.feed;

import com.debbech.generator.model.feed.Post;
import com.debbech.generator.model.feed.PostTag;

import java.util.List;
import java.util.Set;

public interface IPostTagService {

    Set<PostTag> prepareTags(String tags, Post p);

}
