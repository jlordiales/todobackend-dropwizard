package resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import api.Todo;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class TodoResourceTest {
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(new TodoResource())
        .build();

    @Test
    public void shouldRespondAPostWithTheTodoPosted() {
        Todo passedTodo = new Todo("a todo");
        ClientResponse response = resources.client().resource("/").type(APPLICATION_JSON_TYPE).post(
            ClientResponse.class, passedTodo);

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.getEntity(Todo.class)).isEqualTo(passedTodo);
    }

    @Test
    public void shouldReplyWithAnEmptyJsonArray() {
        List<Todo> todos = resources.client().resource("/").type(APPLICATION_JSON_TYPE).get(new GenericType<List<Todo>>(){});
        assertThat(todos).isEmpty();
    }
}