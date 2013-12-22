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
        String result = Mailer.send(mailer -> {
            mailer.from(from)
                    .to(to)
                    .subject(subject)
                    .body(body);
        });

        return Response.ok(result).build();
    }

    // Below is the oldschool way of solving the problem

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

        return Response.ok(mailer.getResult()).build();
    }
}

class Mailer {
    private StringBuffer result;

    public Mailer() {
        result = new StringBuffer();
    }

    public static String send(Consumer<Mailer> block) {
        Mailer mailer = new Mailer();
        block.accept(mailer);

        return mailer.getResult().append(" # sending...").toString();
    }

    public Mailer from(String from) {
        result.append("from: " + from);

        return this;
    }

    public Mailer to(String to) {
        result.append(" # to: " + to);

        return this;
    }

    public Mailer subject(String subject) {
        result.append(" # subject: " + subject);

        return this;
    }

    public Mailer body(String body) {
        result.append(" # body: " + body);

        return this;
    }

    public StringBuffer getResult() {
        return result;
    }
}

class OldschoolMailer {
    private StringBuffer result;

    public OldschoolMailer() {
        result = new StringBuffer();
    }

    public void from(String from) {
        result.append("from: " + from);
    }

    public void to(String to) {
        result.append(" # to: " + to);
    }

    public void subject(String subject) {
        result.append(" # subject: " + subject);
    }

    public void body(String body) {
        result.append(" # body: " + body);
    }

    public void send() {
        result.append(" # sending...");
    }

    public String getResult() {
        return result.toString();
    }
}