package com.redhat.hackfest.serializers;

import java.nio.charset.Charset;
import java.util.Map;
import com.google.gson.Gson;
import com.redhat.hackfest.model.Feed;
import org.apache.kafka.common.serialization.Serializer;

public class FeedSerializer implements Serializer<Feed> {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    static private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Feed feed) {

        // Transform the Person object to String
        String line = gson.toJson(feed);
        // Return the bytes from the String 'line'
        return line.getBytes(CHARSET);

        // byte[] serializedValue = null;
        // ObjectMapper objectMapper = new ObjectMapper();
        // try {
        // serializedValue = objectMapper.writeValueAsString(feed).getBytes();
        // } catch (Exception exception) {
        // System.out.println("Error in serializing Feed: " + feed);
        // }
        // return serializedValue;
    }

    @Override
    public void close() {
    }
}
