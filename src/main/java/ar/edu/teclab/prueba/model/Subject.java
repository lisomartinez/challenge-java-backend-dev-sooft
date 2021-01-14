package ar.edu.teclab.prueba.model;

import java.util.Objects;

public class Subject {
    private String title;
    private int year;


    public Subject( String title, int courseAtYear) {
        this.title = title;
        this.year = courseAtYear;
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
        return getYear() == subject.getYear() && Objects.equals(getTitle(), subject.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getYear());
    }

    @Override
    public String toString() {
        return "Subject{" +
                ", title='" + title + '\'' +
                ", courseAtYear=" + year +
                '}';
    }

}
