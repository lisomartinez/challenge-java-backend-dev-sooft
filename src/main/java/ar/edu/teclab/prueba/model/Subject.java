package ar.edu.teclab.prueba.model;

import java.util.Objects;

public class Subject {
    private String subjectId;
    private String title;
    private int year;


    public Subject(String subjectId, String title, int courseAtYear) {
        this.subjectId = subjectId;
        this.title = title;
        this.year = courseAtYear;
    }


    public String getSubjectId() {
        return subjectId;
    }


    public String getTitle() {
        return title;
    }


    public int getYear() {
        return year;
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
                ", subjectId='" + subjectId + '\'' +
                ", title='" + title + '\'' +
                ", courseAtYear=" + year +
                '}';
    }

}
