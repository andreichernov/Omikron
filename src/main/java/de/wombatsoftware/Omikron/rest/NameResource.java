package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/names/")
@Stateless
public class NameResource {
    List<String> names = Arrays.asList("Ben", "Brad", "Bill", "Kara", "Sara", "Jil", "Brenda");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNames() {
        return Response.ok(names.stream()
                .filter(name -> name.startsWith("B") || name.startsWith("K"))
                .filter(name -> name.length() == 3 || name.length() == 4)
                .collect(toList())).build();
    }
}