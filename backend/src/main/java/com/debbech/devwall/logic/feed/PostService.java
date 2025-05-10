package com.debbech.devwall.logic.feed;

import com.debbech.devwall.logic.ai.IAiFace;
import com.debbech.devwall.model.ai.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
public class PostService implements IPostService{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAiFace aiFace;

    private String getRandomPrompt(){
        return "something that is so useful in java that i dont know about";
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

    @Scheduled(cron = "*/60 * * * * *")
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
}
