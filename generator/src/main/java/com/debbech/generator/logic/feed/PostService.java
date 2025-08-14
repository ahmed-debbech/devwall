package com.debbech.generator.logic.feed;

import com.debbech.generator.database.IPostRepo;
import com.debbech.generator.database.IPostTagRepo;
import com.debbech.generator.logic.ai.IAiFace;
import com.debbech.generator.logic.ai.IInMemoryStore;
import com.debbech.generator.logic.seed.ITopicSeed;
import com.debbech.generator.logic.seed.StackoverflowTopicSeed;
import com.debbech.generator.logic.utils.Crypto;
import com.debbech.generator.model.ai.Task;
import com.debbech.generator.model.ai.WriteRequest;
import com.debbech.generator.model.feed.Post;
import com.debbech.generator.model.feed.PostStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PostService implements IPostService{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${ai.generate}")
    private String generate;

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
    @Autowired
    private ITopicSeed topicSeedService;

    private StackoverflowTopicSeed.TopicHash getRandomPrompt() throws Exception {

        StackoverflowTopicSeed.TopicHash top = this.topicSeedService.consumeTopic();
        String prompt = """
                Write a full comprehensive article about %s.
                The first line should be a generated title about the article you gonna write bellow, placed between square brackets like this: [title].
                The second line should include some tags, enclosed in square brackets and separated by commas, with no spaces, like this: [tag1,tag2,tag3,tag4,...].
                After that, write the article normally in clear, well-structured paragraphs.
                """.formatted(top.topic);

        top.topic = prompt;
        return top;
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
        if(generate.equals("ENABLED")) {
            log.info("generating a new post");
            WriteRequest wr = new WriteRequest();
            wr.setName(generateName());
            String topic = "";
            try {
                StackoverflowTopicSeed.TopicHash tp = getRandomPrompt();
                topic = tp.topic; //prompt
                wr.setTitle_hash(tp.hash);
            }catch(Exception e){
                log.warn("can not generate new topics");
                return;
            }
            wr.setBody(topic);

            log.info("asking ai to generate about this topic {}", topic);
            if (aiFace.addNewOne(wr)) {
                log.info("AI said will do the writing");
            } else {
                log.info("AI said it won't do the job");
            }
        }
    }

    @Transactional
    public Post contructPost(Task s){
        Post p = new Post();
        p = postRepo.save(p);

        System.err.println(s.getWriteResponse().getTags());
        p.setCreatedAt(String.valueOf(Instant.now().toEpochMilli()));
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
}
