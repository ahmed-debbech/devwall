package com.debbech.generator.logic.seed;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StackoverflowTopicSeed implements ITopicSeed{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean enabled = true;
    private int currentPageNumber = 0;
    private List<String> topics = new ArrayList<>();
    private int readCounter = 0;

    @Override
    public String consumeTopic() throws Exception {
        if(this.topics.isEmpty()) throw new Exception("no topics yet from stackoverflow");
        String topic= this.topics.get(readCounter);
        log.info("consumed topic at index {}", readCounter);
        readCounter++;
        return topic;
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
