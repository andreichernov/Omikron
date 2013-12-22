package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Consumer;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/cleanup/")
@Stateless
public class CleanupResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response useResource() {
        String result = Resource.use(resource -> {
            resource.op1();
            resource.op2();
        });

        return Response.ok(result).build();
    }

    // Below is the oldschool way of solving the problem

    @GET
    @Path("/oldschool/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response useResourceOldschool(@QueryParam("close") @DefaultValue("true") Boolean close) {
        OldschoolResource oldschoolResource = new OldschoolResource();
        oldschoolResource.op1();
        oldschoolResource.op2();

        if(close) {
            oldschoolResource.close();
        }

        String result = oldschoolResource.getResult();

        return Response.ok(result).build();
    }
}

class Resource {
    //we want to clean up the object quite deterministically as soon as we're done with it.
    //Java 7 ARM is a step closer to this, but still requires programmers to remember to use
    //the try format.
    //Using EAM pattern the Java 8 compiler can gently force the programmer to naturally use it without having
    //to remember.

    private StringBuffer result;

    private Resource() {
        result = new StringBuffer("Instance created....");
    }

    public void op1() {
        result.append("op1 called....");
    }

    public void op2() {
        result.append("op2 called....");
    }

    private void close() {
        result.append("do any cleanup here...");
    }

    public String getResult() {
        return result.toString();
    }

    public static String use(Consumer<Resource> consume) {
        Resource resource = new Resource();

        try {
            consume.accept(resource);
        } finally {
            resource.close();
        }

        return resource.getResult();
    }
}

class OldschoolResource {
    private StringBuffer result;

    public OldschoolResource() {
        result = new StringBuffer("Instance created....");
    }

    public void op1() {
        result.append("op1 called....");
    }

    public void op2() {
        result.append("op2 called....");
    }

    public void close() {
        result.append("do any cleanup here...");
    }

    public String getResult() {
        return result.toString();
    }
}