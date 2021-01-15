package ar.edu.teclab.prueba.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {

    private long id;

    @NotNull
    private String body;

    private CommentDto(long id, String body) {
        this.id = id;
        this.body = body;
    }

    public CommentDto() {
    }

    public static CommentDto createComment(long id, String body) {
        return new CommentDto(id, body);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;
        CommentDto comment = (CommentDto) o;
        return id == comment.id && body.equals(comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long createdCommentId) {
        id = createdCommentId;
    }
}
