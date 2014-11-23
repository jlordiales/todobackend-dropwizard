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
import io.dropwizard.jersey.PATCH;

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
        Todo newTodo = new Todo(todo.getTitle(), false, todoUri.toASCIIString(),todo.getOrder());

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

    @PATCH
    @Path("/todo/{id}")
    public Response updateTodo(@PathParam("id") int id, Map<String, String> params) {
        Todo todoToUpdate = todos.get(id);
        if (todoToUpdate == null) return Response.status(Response.Status.NOT_FOUND).build();

        String newTitle = params.getOrDefault("title", todoToUpdate.getTitle());
        String newStatus = params.getOrDefault("completed", String.valueOf(todoToUpdate.isCompleted()));
        String newOrder = params.getOrDefault("order", String.valueOf(todoToUpdate.getOrder()));

        Todo newTodo = new Todo(newTitle, Boolean.valueOf(newStatus), todoToUpdate.getUrl(), Integer.valueOf(newOrder));
        todos.replace(id, newTodo);
        return Response.ok(newTodo).build();

    }
}
