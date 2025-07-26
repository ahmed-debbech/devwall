package com.debbech.generator.logic.seed;

import com.debbech.generator.database.IPostRepo;
import com.debbech.generator.database.IWriteRequestRepo;
import com.debbech.generator.logic.utils.Crypto;
import com.debbech.generator.model.ai.WriteRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StackoverflowTopicSeed implements ITopicSeed{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean enabled = true;
    private int currentPageNumber = 0;
    private List<String> topics = new ArrayList<>();
    private int readCounter = 0;

    @Autowired
    private IWriteRequestRepo writeRequestRepo;

    public class TopicHash {
        public String topic;
        public String hash;
    }

    @Override
    public TopicHash consumeTopic() throws Exception {
        if(this.topics.isEmpty()) throw new Exception("no topics yet from stackoverflow");

        TopicHash th = new TopicHash();
        String topic = "";
        boolean new_topic_found = false;
        while(!new_topic_found) {
            topic = this.topics.get(readCounter);
            String sha1 = Crypto.generateSHA1(topic);
            //checking if topic is already used (after hashing the title and comparing it)
            WriteRequest wr = writeRequestRepo.retrieveByHash(sha1).orElse(null);
            if (wr != null) {
                log.error("topic at index {} is already consumed... jumping to next topic", readCounter);
                readCounter++;
                continue;
            }
            new_topic_found = true;
            th.topic = topic;
            th.hash = sha1;
            log.info("consumed topic at index {}", readCounter);
            readCounter++;
            if (readCounter > this.topics.size() - 1) {
                readCounter = 0;
            }
        }
        return th;
    }


    @Scheduled(cron = "*/10 * * * * *")
    private void retrieveNewQuestions(){

        if(!enabled) return;

        log.info("scrapping page number {} from stackoverflow", currentPageNumber);
        Document doc = null;
        try {
            doc = Jsoup.connect("https://stackoverflow.com/questions?page="+currentPageNumber).get();
        } catch (IOException e) {
            log.error("could not connect to StackOverflow to retrieve new questions");
            return;
        }
        if(this.currentPageNumber >= 50){
            this.enabled = false;
        }
        Elements questions = doc.select("#questions");

        Elements elements = questions.select("[id^=question-summary-]");
        for(Element el : elements){
            String qst = el.select(".s-post-summary--content-title").text();
            topics.add(qst);
        }
        log.info("done scapping page {} from stackoverflow", currentPageNumber);
        this.currentPageNumber++;
    }
}
