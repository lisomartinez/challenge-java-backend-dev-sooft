package ar.edu.teclab.prueba.model;

import ar.edu.teclab.prueba.shared.DomainException;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Degree {
    private Long id;
    private String degreeId;
    private String title;
    private DegreeType type;
    private Director director;
    private Set<Subject> studyPlan;

    @SuppressWarnings("unused")

//    public Degree(String degreeId, String title, DegreeType type, Director director) {
//        this(null, degreeId, title, type, director);
//    }
//
//    public Degree(Long id, String degreeId, String title, DegreeType type, Director director) {
//        this(id, degreeId, title, type, director, new HashSet<>());
//
//    }

    public Degree(Long id, String degreeId, String title, DegreeType type, Director director, Set<Subject> studyPlan) {
        this.id = id;
        this.degreeId = degreeId;
        this.title = title;
        this.type = type;
        this.director = director;
        this.studyPlan = studyPlan;
    }


    public static Degree createDegree(String title, DegreeType type, Director director, Set<Subject> studyPlan) {
        assertThatTitleIsPresent(title);
        assertThatHasADirector(director);
        return new Degree(null, null, title, type, director, studyPlan);
    }

//    public static Degree createDegree(String title, DegreeType type, Director director, Set<Subject> studyPlan) {
//        assertThatTitleIsPresent(title);
//        assertThatHasADirector(director);
//        return new Degree(null, null, title, type, director, studyPlan);
//    }

//    public static Degree createDegree(String directorId, String title, DegreeType type, Director director) {
//        assertThatIdHasUUIDFormat(directorId);
//        assertThatTitleIsPresent(title);
//        assertThatHasADirector(director);
//        return new Degree(null, directorId, title, type, director);
//    }

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

    public static Degree createDegree(String degreeId,
                                      String title,
                                      DegreeType type,
                                      Director director,
                                      Set<Subject> studyPlan
    ) {
        assertThatIdHasUUIDFormat(degreeId);
        assertThatTitleIsPresent(title);
        assertThatHasADirector(director);
        return new Degree(null, degreeId, title, type, director, studyPlan);
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

    public String getDegreeId() {
        return degreeId;
    }

    public Long getId() {
        return id;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setDegreeId(String aDirectorsId) {
        degreeId = aDirectorsId;
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

    public void addSubjectToStudyPlan(Subject subject) {
        this.studyPlan.add(subject);
    }

    public void removeSubjectFromStudyPlan(Subject subject) {
        this.studyPlan.remove(subject);
    }

    public Set<Subject> getStudyPlan() {
        return Collections.unmodifiableSet(studyPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDegreeId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Degree)) return false;
        Degree degree = (Degree) o;
        return getDegreeId().equals(degree.getDegreeId());
    }

    @Override
    public String toString() {
        return "Degree{" +
                "id=" + id +
                ", degreeId='" + degreeId + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", director=" + director +
                '}';
    }
}
