package resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.Todo;
import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import service.TodoApplication;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class TodoResourceTest {
    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
        new DropwizardAppRule<>(TodoApplication.class, "src/main/resources/config.yaml");

    private Client client = new Client();
    private String rootPath;

    @Before
    public void setup() {
        rootPath = String.format("http://localhost:%d/todos", RULE.getLocalPort());
        client.resource(rootPath).delete();
    }


    @Test
    public void shouldRespondAPostWithTheTodoPostedAndStatusNotComplete() {
        Todo passedTodo = new Todo("a todo");

        ClientResponse response = saveTodo(passedTodo);

        Todo responseEntity = response.getEntity(Todo.class);

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(responseEntity.getTitle()).isEqualTo(passedTodo.getTitle());
        assertThat(responseEntity.isCompleted()).isFalse();
    }

    @Test
    public void shouldStoreTheTodoPosted() {
        Todo passedTodo = new Todo("a todo");
        saveTodo(passedTodo);

        List<Todo> todos = getTodos();

        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getTitle()).isEqualTo(passedTodo.getTitle());
    }

    @Test
    public void shouldReplyWithAnEmptyJsonArray() {
        List<Todo> todos = getTodos();

        assertThat(todos).isEmpty();
    }

    @Test
    public void shouldReturnTheUrlOfTheTodos() {
        Todo firstTodo = new Todo("first todo");
        Todo secondTodo = new Todo("second todo");

        saveTodo(firstTodo);
        saveTodo(secondTodo);

        List<Todo> todos = getTodos();

        Todo returnedTodo = getTodo(todos.get(0).getUrl());

        assertThat(returnedTodo.getTitle()).isEqualTo("first todo");
    }

    @Test
    public void shouldDeleteATodo() {
        Todo todo = new Todo("todo");
        saveTodo(todo);

        List<Todo> todos = getTodos();
        assertThat(todos).hasSize(1);

        deleteTodo(todos.get(0).getUrl());

        assertThat(getTodos()).isEmpty();
    }

    @Test
    @Ignore("Amazingly the HttpURLConnection class doesnt support PATCH...")
    public void shouldChangeTheTitleByPatchingToTheUrl() {
        Todo todo = new Todo("todo");
        saveTodo(todo);

        List<Todo> todos = getTodos();

        Map<String, String> params = new HashMap<>();
        params.put("title", "changed");
        client.resource(todos.get(0).getUrl()).type(APPLICATION_JSON_TYPE).method("patch", params);

        assertThat(getTodo(todos.get(0).getUrl()).getTitle()).isEqualTo("changed");
    }

    @Test
    @Ignore("Amazingly the HttpURLConnection class doesnt support PATCH...")
    public void shouldChangeTheCompleteStatusByPatchingToTheUrl() {
        Todo todo = new Todo("todo");
        saveTodo(todo);

        List<Todo> todos = getTodos();

        Map<String, String> params = new HashMap<>();
        params.put("completed", "true");
        client.resource(todos.get(0).getUrl()).type(APPLICATION_JSON_TYPE).method("PATCH", params);

        assertThat(getTodo(todos.get(0).getUrl()).isCompleted()).isTrue();
    }

    private Todo getTodo(URI url) {
        return client.resource(url).type(APPLICATION_JSON_TYPE).get(
            Todo.class);
    }

    private List<Todo> getTodos() {
        return client.resource(rootPath).type(APPLICATION_JSON_TYPE).get(
            new GenericType<List<Todo>>() {
            });
    }

    private ClientResponse saveTodo(Todo passedTodo) {
        return client.resource(rootPath).type(APPLICATION_JSON_TYPE).post(
            ClientResponse.class, passedTodo);
    }

    private ClientResponse deleteTodo(URI url) {
        return client.resource(url).type(APPLICATION_JSON_TYPE).delete(ClientResponse.class);
    }
}