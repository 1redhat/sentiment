package org.acme;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
// import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/twitter")
@ApplicationScoped
public class CamelResource {

    // @ConfigProperty(name = "twitter.user.name")
    // String twitterUserName;

    @Inject
    ProducerTemplate producerTemplate;

    @Inject
    ConsumerTemplate consumerTemplate;

    @Path("/timeline")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTimeline(@QueryParam("topic") String topic, @QueryParam("sample") Integer sample) {

        final String uri = "twitter-search://#" + topic + "?"
          // + "?user=mrjonleek"
          + "accessToken=792541664-dYEgUQdihiO2Q44ZwHxl6zTWz0xO4Jh4qJKdyZMw"
          + "&accessTokenSecret=j1Fsu6ESW1ojFqOW7nT4W7frzNladZUUWpd2aP3vzpVZF"
          + "&consumerKey=ipAr9AVYyfrf82ua2o9mxaay4"
          + "&consumerSecret=lboMled36TLn8AhEtbzcBcrzfOdhNqGE8EGVC6pKmDpfEJe1V6"
          // + "&count=1"
          + "&lang=en-us";

        final List<String> tweets = new ArrayList<String>();
        final List<String> tags = new ArrayList<String>();

        // Iterate over sample size to build dataset
        for (int i = 0; i < sample; i++) {
          // Perform query
          // String tweet = consumerTemplate.receiveBodyNoWait(String.format(uri), String.class);
          String tweet = consumerTemplate.receiveBody(String.format(uri), String.class);
          // Keep the original tweet
          tweets.add(tweet);
          // Pattern match hashtag
          Matcher matcher = Pattern.compile("#[a-zA-Z0-9-_]*").matcher(tweet);
          // Iterate over matches
          while (matcher.find()) {
            tags.add(matcher.group());
          }
        }

        System.out.println(tags);
        return tweets.toString();
    }
}
