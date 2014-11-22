package api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TodoTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void shouldDeserializeJson() throws IOException {
        final Todo expectedTodo = new Todo("a todo");
        final Todo deserializedTodo = MAPPER.readValue(fixture("fixtures/todo.json"), Todo.class);

        assertThat(deserializedTodo).isEqualTo(expectedTodo);
    }

    @Test
    public void shouldSerializeObject() throws IOException {
        final Todo todo = new Todo("a todo");
        String serializedTodo = MAPPER.writeValueAsString(todo);
        assertThat(serializedTodo).isEqualTo(fixture("fixtures/todo.json"));
    }
}
