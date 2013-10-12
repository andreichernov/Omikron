package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/total/")
@Stateless
public class TotalResource {
    List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);

    public static int totalValues(List<Integer> numbers, Predicate<Integer> selector) {
        return numbers.stream()
                .filter(selector)
                .mapToInt(value -> value)
                .sum();
    }

    // BAD STYLE
    public static int totalValues(List<Integer> numbers) {
        int total = 0;

        for (int number : numbers) {
            total += number;
        }

        return total;
    }

    // BAD STYLE
    public static int totalEvenValues(List<Integer> numbers) {
        int total = 0;

        for (int number : numbers) {
            if (number % 2 == 0) total += number;
        }

        return total;
    }

    // BAD STYLE
    public static int totalOddValues(List<Integer> numbers) {
        int total = 0;

        for (int number : numbers) {
            if (number % 2 != 0) total += number;
        }

        return total;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotal(@QueryParam("odd") Boolean odd) {
        int response = 0;

        if (odd == null) {
            response = totalValues(values, value -> true);
        } else if (odd == true) {
            response = totalValues(values, value -> value % 2 != 0);
        } else {
            response = totalValues(values, value -> value % 2 == 0);
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("/oldschool/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalOldschool(@QueryParam("odd") Boolean odd) {
        int response = 0;

        if (odd == null) {
            response = totalValues(values);
        } else if (odd == true) {
            response = totalOddValues(values);
        } else {
            response = totalEvenValues(values);
        }

        return Response.ok(response).build();
    }
}