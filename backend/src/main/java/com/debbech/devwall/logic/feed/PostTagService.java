package com.debbech.devwall.logic.feed;

import com.debbech.devwall.database.IPostTagRepo;
import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.feed.PostTag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PostTagService implements IPostTagService{

    @Autowired
    private IPostTagRepo tagRepo;

    @Transactional
    @Override
    public Set<PostTag> prepareTags(String tags, Post p) {

        String[] tags_splitted = tags.split(",");

        Set<PostTag> postTags = new HashSet<>();

        for(int i=0; i<= tags_splitted.length-1; i++) {
            PostTag ptdb = tagRepo.findByName(tags_splitted[i].trim()).orElse(null);
            if(ptdb == null) {
                PostTag pt = new PostTag();
                pt.setName(tags_splitted[i].trim());
                pt.getPosts().add(p);
                postTags.add(pt);
            }else{
                ptdb.getPosts().add(p);
                postTags.add(ptdb);
            }
        }

        for(PostTag pt : postTags){
            tagRepo.save(pt);
        }

        return postTags;
    }
}
