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
        final Todo expectedTodo = new Todo("simple todo");
        final Todo deserializedTodo = MAPPER.readValue(fixture("fixtures/simple.json"), Todo.class);

        assertThat(deserializedTodo).isEqualTo(expectedTodo);
    }

    @Test
    public void shouldDeserializePartialRepresentation() throws IOException {
        final Todo expectedTodo = new Todo(null,null,null,99,null);
        final Todo deserializedTodo = MAPPER.readValue(fixture("fixtures/partialUpdate.json"), Todo.class);

        assertThat(deserializedTodo).isEqualTo(expectedTodo);
    }

    @Test
    public void shouldSerializeObject() throws IOException {
        final Todo todo = new Todo("a todo",true,null,15,null);
        String serializedTodo = MAPPER.writeValueAsString(todo);
        assertThat(serializedTodo).isEqualTo(fixture("fixtures/todo.json"));
    }
}
