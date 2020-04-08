package com.redhat.hackfest.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import com.google.gson.Gson;
import com.redhat.hackfest.model.Feed;
import com.redhat.hackfest.utils.TagGenerator;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;

@ApplicationScoped
public class FeedGenerator {
    @Outgoing("twitter-feeds")
    /**
     * Generate feeds and send it to the topic "twitter-feeds"
     * 
     * @return
     */
    public Flowable<Feed> generate() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(tick -> new Feed(TagGenerator.generate(), LocalDateTime.now() + ""));
        // for testing purposes, only 15 feeds are created
        // remove take(15) for continuous feed
    }

    public static void main(String[] args) {
        Feed f = new Feed("test", LocalDateTime.now() + "");
        System.out.println(new Gson().toJson(f));

    }
}
