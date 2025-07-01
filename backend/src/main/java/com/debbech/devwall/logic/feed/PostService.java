package com.debbech.devwall.logic.feed;

import com.debbech.devwall.database.IPostRepo;
import com.debbech.devwall.database.IPostTagRepo;
import com.debbech.devwall.logic.ai.IAiFace;
import com.debbech.devwall.logic.ai.IInMemoryStore;
import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.feed.Post;
import com.debbech.devwall.model.feed.PostStatus;
import com.debbech.devwall.model.search.SearchedPost;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    private static final String[] TOPICS = {
            "Give me something I don't know about Java memory management, especially regarding how the JVM optimizes garbage collection for high-performance applications.",
            "What are the best tips for writing highly concurrent code in Go without running into race conditions or goroutine leaks?",
            "How to write SQL queries that remain fast and efficient as the database grows to hundreds of millions of rows?",
            "Give me something I don’t know about designing resilient microservices that can recover from failure without manual intervention.",
            "What are the best practices for structuring a large-scale Go project so that it remains testable, modular, and maintainable?",
            "How to optimize Java applications running on cloud infrastructure to reduce both latency and resource consumption?",
            "Give me something I don’t know about SQL indexes—how composite and partial indexes can outperform regular indexing strategies in real use cases.",
            "What are the best tips for debugging complex bugs in distributed systems when logs are incomplete or misleading?",
            "How to implement and test ACID-compliant transactional logic in Go using PostgreSQL?",
            "Give me something I don’t know about Java Streams—especially hidden performance traps or advanced use cases in processing large datasets."
    };

    private String getRandomPrompt(){
        Random random = new Random();
        int index = random.nextInt(TOPICS.length);
        return TOPICS[index];
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

    //@Scheduled(cron = "0 0/10 * * * *")
    @Scheduled(cron = "*/30 * * * * *")
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

        //log.info("Flushing to database ....");

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
                p.setRandomId(generatePostRandId());
                postRepo.save(p);
                inMemoryStore.deleteOne(taskList.get(tasksIndex[i]));
            } catch (Exception e) {
                log.error("Could not save the post to database OR not delete post from inmemroy database " + e.getMessage());
            }
            i++;
        }

    }

    public static String generatePostRandId() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

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
