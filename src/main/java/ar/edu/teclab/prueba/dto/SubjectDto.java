package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Subject;

import java.util.Objects;

public class SubjectDto {
    private String subjectId;
    private String title;
    private int year;

    public SubjectDto() {
    }

    public SubjectDto(String subjectId, String title, int year) {
        this.subjectId = subjectId;
        this.title = title;
        this.year = year;
    }

    public static SubjectDto from(Subject subject) {
        return new SubjectDto( subject.getSubjectId(), subject.getTitle(), subject.getYear());
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
        if (!(o instanceof SubjectDto)) return false;
        SubjectDto that = (SubjectDto) o;
        return getYear() == that.getYear() && getSubjectId().equals(that.getSubjectId()) && getTitle()
                .equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectId(), getTitle(), getYear());
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                ", subjectId='" + subjectId + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }

    public Subject toEntity() {
        return new Subject(subjectId, title, year);
    }
}
