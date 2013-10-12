package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Consumer;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/mail/")
@Stateless
public class MailResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMail(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("subject") String subject, @QueryParam("body") String body) {
        Mailer.send(mailer -> {
            mailer.from(from)
                    .to(to)
                    .subject(subject)
                    .body(body);
        });

        return Response.noContent().build();
    }

    @GET
    @Path("/oldschool/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMailOldschool(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("subject") String subject, @QueryParam("body") String body) {
        OldschoolMailer mailer = new OldschoolMailer();

        mailer.from(from);
        mailer.to(to);
        mailer.subject(subject);
        mailer.body(body);
        mailer.send();

        return Response.noContent().build();
    }
}

class OldschoolMailer {
    public void from(String from) {
        System.out.println("from: " + from);
    }

    public void to(String to) {
        System.out.println("to: " + to);
    }

    public void subject(String subject) {
        System.out.println("subject: " + subject);
    }

    public void body(String body) {
        System.out.println("body: " + body);
    }

    public void send() {
        System.out.println("sending...");
    }
}

class Mailer {
    public static void send(Consumer<Mailer> block) {
        Mailer mailer = new Mailer();
        block.accept(mailer);
        System.out.println("sending...");
    }

    public Mailer from(String from) {
        System.out.println("from: " + from);

        return this;
    }

    public Mailer to(String to) {
        System.out.println("to: " + to);

        return this;
    }

    public Mailer subject(String subject) {
        System.out.println("subject: " + subject);

        return this;
    }

    public Mailer body(String body) {
        System.out.println("body: " + body);

        return this;
    }
}