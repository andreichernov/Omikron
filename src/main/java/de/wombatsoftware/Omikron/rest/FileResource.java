package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * @author Peter Bence Marfoldi
 * @date 10.10.13 15:15
 */

@Path("/files/")
@Stateless
public class FileResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFiles(@QueryParam("path") @DefaultValue(".") String path) throws IOException {
		return Response.ok(Files.walk(Paths.get(path))
				.filter(Files::isRegularFile)
				.collect(toList())).build();
	}

	// Below is the oldschool way of solving the problem

	@GET
	@Path("/oldschool/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilesOldschool(@QueryParam("path") @DefaultValue(".") String path) {
		File[] filesAndDirectories = new File(path).listFiles();
		List<File> result = new ArrayList<>();

		for (int i = 0; i < filesAndDirectories.length; ++i) {
			if (filesAndDirectories[i].isFile()) {
				result.add(filesAndDirectories[i]);
			}
		}
		
		return Response.ok(result).build();
	}
}