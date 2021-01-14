package ar.edu.teclab.prueba.dto;

import java.util.Map;
import java.util.Objects;

public class CommentDto {
    private Map<String, Object> comments;

    public CommentDto() {
    }

    public CommentDto(Map<String, Object> comments) {
        this.comments = comments;
    }

    public Map<String, Object> getComments() {
        return comments;
    }

    public void setComments(Map<String, Object> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComments());
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "comments=" + comments +
                '}';
    }
}
