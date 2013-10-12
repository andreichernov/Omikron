package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
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
    List<String> names = Arrays.asList("Ben", "Brad", "Bill", "Kim", "Kara", "Sam", "Sara", "Jil", "Brenda");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNames(@QueryParam("startsWith") @DefaultValue("B") List<String> letterList, @QueryParam("length") @DefaultValue("3") List<Integer> lengthList) {
        return Response.ok(names.stream()
                .filter(name -> letterList.stream().anyMatch(letter -> name.startsWith(letter)))
                .filter(name -> lengthList.stream().anyMatch(length -> name.length() == length))
                .collect(toList())).build();
    }
}