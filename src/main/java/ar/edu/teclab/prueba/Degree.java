package ar.edu.teclab.prueba;

import java.util.Objects;
import java.util.UUID;

public class Degree {
    private final String id;
    private final String title;
    private final DegreeType type;
    private final Director director;

    private Degree(String id, String title, DegreeType type, Director director) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.director = director;
    }

    public static Degree createDegree(String id, String title, DegreeType type, Director director) {
        assertThatIdHasUUIDFormat(id);
        assertThatTitleIsPresent(title);
        assertThatHasADirector(director);
        return new Degree(id, title, type, director);
    }

    private static void assertThatHasADirector(Director director) {
        if (director == null) throw new DomainException("Cannot create a Degree without a Director.");
    }

    private static void assertThatIdHasUUIDFormat(String id) {
        if (id == null || id.trim().isEmpty()) throw new DomainException("Cannot create a Degree without an id.");
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new DomainException("Degree Id should have UUID format");
        }

    }

    private static void assertThatTitleIsPresent(String title) {
        if (title == null || title.trim().isEmpty()) throw new DomainException("Cannot create a Degree without title.");
    }

    public String getTitle() {
        return title;
    }

    public DegreeType getType() {
        return type;
    }

    public Director getDirector() {
        return director;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Degree)) return false;
        Degree degree = (Degree) o;
        return getId().equals(degree.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
