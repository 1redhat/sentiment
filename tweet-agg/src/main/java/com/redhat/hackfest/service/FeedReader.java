package com.redhat.hackfest.service;

import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import com.redhat.hackfest.model.Feed;
import com.redhat.hackfest.serializers.FeedSeder;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;


@ApplicationScoped
public class FeedReader {
    private static final String TWITTER_FEEDS_TOPIC = "twitter-feeds";
    private static final String FEEDS_AGGREGATED_TOPIC = "agg-feeds";

    @Produces
    /**
     * Build a topology that does the following
     * 
     * @return
     */
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        FeedSeder feedSeder = new FeedSeder();

        builder.<String, Feed>stream(TWITTER_FEEDS_TOPIC, Consumed.with(Serdes.String(), feedSeder))
                .map((key, feed) -> new KeyValue<>(feed.getHashtag().toString(), feed)).groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10))).count().toStream()
                .map((Windowed<String> key, Long count) -> new KeyValue(key.key(),
                        key.key() + ":" + count.toString()))
                .to(FEEDS_AGGREGATED_TOPIC, Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }
}
