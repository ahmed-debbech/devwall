package com.debbech.devwall.logic.feed;

import com.debbech.devwall.database.IPostRepo;
import com.debbech.devwall.database.IPostTagRepo;
import com.debbech.devwall.logic.ai.IAiFace;
import com.debbech.devwall.logic.ai.IInMemoryStore;
import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.feed.PostStatus;
import com.debbech.devwall.model.feed.PostTag;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class PostService implements IPostService{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAiFace aiFace;
    @Autowired
    private IInMemoryStore inMemoryStore;
    @Autowired
    private IPostRepo postRepo;
    @Autowired
    private IPostTagRepo tagRepo;
    @Autowired
    private IPostTagService postTagService;


    private String getRandomPrompt(){
        return "describe what is java in two lines";
    }

    private String generateName(){
        int length = 6;
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }

    @Scheduled(cron = "* */10 * * * *")
    @Override
    public void generateNewPost() {
        log.info("generating a new post");
        WriteRequest wr = new WriteRequest();
        wr.setName(generateName());
        String topic = getRandomPrompt();
        wr.setBody(topic);

        log.info("asking ai to generate about this topic {}", topic);
        if (aiFace.addNewOne(wr)){
            log.info("AI said will do the writing");
        }else{
            log.info("AI said it won't do the job");
        }

    }

    @Transactional
    public Post contructPost(Task s){
        Post p = new Post();
        p = postRepo.save(p);

        System.err.println(s.getWriteResponse().getTags());
        p.setCreatedAt(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        p.setWriteRequest(s.getWriteRequest());
        p.setWriteResponse(s.getWriteResponse());

        if(s.getWriteResponse().getTags() == null){
            p.setStatus(PostStatus.GETTING_TAGS.name());
        }else{
            p.setTags(this.postTagService.prepareTags(s.getWriteResponse().getTags(), p));
        }
        if(s.getWriteResponse().getTitle() == null){
            p.setStatus(PostStatus.GETTING_TITLE.name());
        }else{
            p.setTitle(s.getWriteResponse().getTitle());
        }
        if(s.getWriteResponse().getPlainResponse() == null){
            p.setStatus(PostStatus.GETTING_BODY.name());
        }else{
            p.setBody(s.getWriteResponse().getPlainResponse());
        }

        if(((s.getWriteResponse().getTags() != null) && (s.getWriteResponse().getTitle() != null) && (s.getWriteResponse().getPlainResponse() != null))){
            p.setStatus(PostStatus.DONE.name());
        }
        return p;
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    @Transactional
    public void flushToDb() {

        log.info("Flushing to database ....");

        List<Task> taskList = inMemoryStore.getAll();
        List<Post> posts = new ArrayList<>();
        int[] tasksIndex = new int[taskList.size()];
        int i = -1;
        int k = 0;
        for(Task s : taskList){
            i++;
            if(s.getEndingTime() <= 0) continue;

            posts.add(contructPost(s));
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

    }

    @Override
    public List<Post> getAllPaginated(int page_number) {

        Pageable page = PageRequest.of(page_number, 5);
        List<Post> posts = postRepo.getAllDonePosts(page);
        return posts;
    }
}
