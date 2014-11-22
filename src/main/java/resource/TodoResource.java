package resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    @GET
    public String hello() {
        return "Hello World 2";
    }

    @POST
    public Response post() {
        return Response.ok().build();
    }

    
}
