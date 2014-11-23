package resources;

import static java.util.Collections.emptyList;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.Todo;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @GET
    public List<Todo> hello() {
        return emptyList();
    }

    @POST
    public Response post(Todo todo) {
        return Response.ok(todo).build();
    }

    @DELETE
    public Response delete() {
        return Response.ok().build();
    }

    
}
