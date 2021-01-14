package ar.edu.teclab.prueba.model;

import ar.edu.teclab.prueba.dto.SubjectDto;

import java.util.Objects;

public class Subject {
    private Long id;
    private String subjectId;
    private String title;
    private int year;

    public Subject(String subjectId, String title, int courseAtYear) {
        this(null, subjectId, title, courseAtYear);
    }

    public Subject(Long id, String subjectId, String title, int courseAtYear) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.year = courseAtYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return getSubjectId().equals(subject.getSubjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectId());
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectId='" + subjectId + '\'' +
                ", title='" + title + '\'' +
                ", courseAtYear=" + year +
                '}';
    }

}
