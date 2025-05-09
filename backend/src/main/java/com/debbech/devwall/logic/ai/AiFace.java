package com.debbech.devwall.logic.ai;

import com.debbech.devwall.database.IPostRepo;
import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AiFace implements IAiFace{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IQueueProcessor queueProcessor;
    @Autowired
    private IInMemoryStore inMemoryStore;
    @Autowired
    private IPostRepo postRepo;

    @Override
    public boolean addNewOne(WriteRequest writeRequest) {

        if(!inMemoryStore.addOne(new Task(writeRequest))) return false;
        queueProcessor.add(writeRequest);
        return true;

    }

    @Scheduled(fixedDelay = 5000)
    @Override
    public int flushToDb() {

        log.info("Flushing to database ....");

        List<Task> taskList = inMemoryStore.getAll();
        List<Post> posts = new ArrayList<>();
        int[] tasksIndex = new int[taskList.size()];
        int i = -1;
        int k = 0;
        for(Task s : taskList){
            i++;
            if(s.getEndingTime() <= 0) continue;

            Post p = new Post();
            p.setBody(s.getWriteResponse().getPlainResponse());
            //TODO: we can invoke code to extract title + tags
            p.setTitle("this is a title");
            p.setCreatedAt(String.valueOf(new Date().getTime()));
            p.setWriteRequest(s.getWriteRequest());
            p.setWriteResponse(s.getWriteResponse());
            posts.add(p);
            tasksIndex[k] = i;
            k++;
        }

        i = 0;
        for(Post p : posts) {
            try {
                postRepo.save(p);
                inMemoryStore.deleteOne(taskList.get(tasksIndex[i]));
            } catch (Exception e) {
                log.error("Could not save the post to database OR not delete post from inmemroy database " + e.getMessage());
            }
            i++;
        }

        return 0;
    }
}
