package resources;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import api.Todo;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
    private final AtomicInteger todosCount = new AtomicInteger(1);
    private final Map<Integer, Todo> todos = new ConcurrentHashMap<>();

    @GET
    public Collection<Todo> getAllTodos() {
        return todos.values();
    }

    @GET
    @Path("/todo/{id}")
    public Todo getTodo(@PathParam("id") int id) {
        return todos.get(id);
    }

    @POST
    public Response createTodo(Todo todo, @Context UriInfo uriInfo) {
        int todoId = todosCount.getAndIncrement();
        URI todoUri = uriInfo.getBaseUriBuilder().path(TodoResource.class, "getTodo").build(todoId);
        Todo newTodo = new Todo(todo.getTitle(), false, todoUri.toASCIIString());

        todos.put(todoId, newTodo);
        return Response.ok(newTodo).build();
    }

    @DELETE
    public Response deleteAll() {
        todos.clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("/todo/{id}")
    public Response delete(@PathParam("id") int id) {
        todos.remove(id);
        return Response.ok().build();
    }

    
}
