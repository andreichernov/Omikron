package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/camera/")
@Stateless
public class CameraResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response takeImage(@QueryParam("filter") @DefaultValue("") String filter) {
        final Camera camera = new Camera();
        final Message message = new Message();
        final Consumer<String> printCaptured = (filterInfo) -> message.message = String.format("with %s : %s", filterInfo, camera.capture(new Color(200, 100, 200)));

        String filterName = "NO filters";

        switch (filter) {
            case "brighter":
                camera.setFilters(Color::brighter);
                filterName = "bright filter";
                break;

            case "darker":
                camera.setFilters(Color::darker);
                filterName = "darker filter";
                break;

            case "both":
                camera.setFilters(Color::brighter, Color::darker);
                filterName = "bright and darker filter";
                break;
        }

        printCaptured.accept(filterName);

        return Response.ok(message.message).build();
    }
}

class Message {
    public String message;
}

class Camera {
    private Function<Color, Color> filter;

    public Camera() {
        setFilters();
    }

    public Color capture(final Color inputColor) {
        final Color processedColor = filter.apply(inputColor);
        //... more processing...
        return processedColor;
    }

    public void setFilters(final Function<Color, Color>... filters) {
        filter = Arrays.asList(filters).stream()
                .reduce((filter, next) -> filter.compose(next))
                .orElse(color -> color);
    }
}