package com.redhat.hackfest.serializers;

import java.nio.charset.Charset;
import java.util.Map;
import com.google.gson.Gson;
import com.redhat.hackfest.model.Feed;
import org.apache.kafka.common.serialization.Deserializer;

public class FeedDeserializer implements Deserializer<Feed> {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    static private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Feed deserialize(String topic, byte[] bytes) {

        try {
            // Transform the bytes to String
            String feed = new String(bytes, CHARSET);
            // Return the Person object created from the String 'person'
            return gson.fromJson(feed, Feed.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading bytes", e);
        }
        // ObjectMapper mapper = new ObjectMapper();
        // Feed feed = null;
        // try {
        // feed = mapper.readValue(bytes, Feed.class);
        // } catch (Exception exception) {
        // System.out.println("Error in deserializing bytes " + exception);
        // }
        // return feed;

    }

    @Override
    public void close() {
    }
}
