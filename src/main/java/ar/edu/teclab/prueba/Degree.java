package ar.edu.teclab.prueba;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;

public class Degree {
    @JsonIgnore
    private Long id;
    private String directorId;
    private String title;
    private DegreeType type;
    private final Director director;


    private Degree(Long id, String directorId, String title, DegreeType type, Director director) {
        this.id = id;
        this.directorId = directorId;
        this.title = title;
        this.type = type;
        this.director = director;
    }
    public static Degree createDegree(String title, DegreeType type, Director director) {
        return new Degree(null, null, title, type, director);
    }
    public static Degree createDegree(String directorId, String title, DegreeType type, Director director) {
        assertThatIdHasUUIDFormat(directorId);
        assertThatTitleIsPresent(title);
        assertThatHasADirector(director);
        return new Degree(null, directorId, title, type, director);
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

    public String getDirectorId() {
        return directorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Degree)) return false;
        Degree degree = (Degree) o;
        return getDirectorId().equals(degree.getDirectorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDirectorId());
    }

    public void setDirectorId(String aDirectorsId) {
        directorId = aDirectorsId;
    }

    public void setId(Long anId) {
        id = anId;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setType(DegreeType newType) {
        type = newType;
    }
}
