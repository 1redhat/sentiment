package com.redhat;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@Path("twit")
public class TwitResource {

    @Inject
    @Channel("twit-out")
    Emitter<String> emitter;
    @Inject
    @Channel("twit-in")
    Publisher<String> txt;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void post(String txt) {
        System.out.println("sending: " + txt);
        emitter.send(txt);
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<String> stream() {
        System.out.println("recieved: " + txt);
        return txt;
    }

}


