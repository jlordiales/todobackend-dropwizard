package api;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

import java.net.URI;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {
    private final String title;
    private final Boolean completed;
    private final Integer order;
    private final URI url;
    private final Integer id;

    public Todo(String title) {
        this(title, null, null, null,null);
    }

    @JsonCreator
    public Todo(@JsonProperty("title")String title,
        @JsonProperty("completed") Boolean completed,
        @JsonProperty("url") URI url,
        @JsonProperty("order") Integer order,
        @JsonProperty("id") Integer id) {
        this.title = title;
        this.completed = completed;
        this.url = url;
        this.order = order;
        this.id = id;
    }

    public Todo update(Todo newTodo) {
        String newTitle = ofNullable(newTodo.getTitle()).orElse(title);
        Boolean newStatus = ofNullable(newTodo.isCompleted()).orElse(completed);
        Integer newOrder = ofNullable(newTodo.getOrder()).orElse(order);

        return new Todo(newTitle, newStatus, url, newOrder, id);
    }

    public String getTitle() {
        return title;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public URI getUrl() {
        return url;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
