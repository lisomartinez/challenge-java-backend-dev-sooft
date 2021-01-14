package ar.edu.teclab.prueba.model;

import java.util.*;

public class Degree extends Entity {
    private String degreeId;
    private String title;
    private DegreeType type;
    private Director director;
    private Set<Subject> studyPlan;

    private Degree(Long id, String degreeId, String title, DegreeType type, Director director, Set<Subject> studyPlan) {
        super(id);
        this.degreeId = degreeId;
        this.title = title;
        this.type = type;
        this.director = director;
        this.studyPlan = studyPlan;
    }

    public Degree(DegreeBuilder builder) {
        super(builder.id);
        this.degreeId = builder.degreeId;
        this.title = builder.title;
        this.type = builder.type;
        this.director = builder.director;
        this.studyPlan = builder.studyPlan;
    }

    public static DegreeBuilder aDegree() {
        return new DegreeBuilder();
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

    public void setId(Long id) {
        this.id = id;
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

    public static class DegreeBuilder {
        private Long id;
        private String degreeId = null;
        private String title;
        private DegreeType type;
        private Director director;
        private Set<Subject> studyPlan = new HashSet<>();

        public DegreeBuilder setId(Long id) {
            this.id = id;
            return this;
        }
        public DegreeBuilder setDegreeId(String degreeId) {
            assertThatIdHasUUIDFormat(degreeId);
            this.degreeId = degreeId;
            return this;
        }
        public DegreeBuilder setTitle(String title) {
            assertThatTitleIsPresent(title);
            this.title = title;
            return this;
        }
        public DegreeBuilder setType(DegreeType type) {
            this.type = type;
            return this;
        }
        public DegreeBuilder setDirector(Director director) {
            assertThatHasADirector(director);
            this.director = director;
            return this;
        }
        public DegreeBuilder setStudyPlan(Set<Subject> studyPlan) {
            this.studyPlan = studyPlan;
            return this;
        }

        public Degree build() {
            return new Degree(this);
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
    }
}
