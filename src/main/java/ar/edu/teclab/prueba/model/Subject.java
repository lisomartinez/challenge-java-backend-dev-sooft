package ar.edu.teclab.prueba.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Subject {
    private String title;
    private int year;

    public Subject() {
    }

    public Subject(String title, int courseAtYear) {
        this.title = title;
        this.year = courseAtYear;
    }


    public String getTitle() {
        return title;
    }


    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
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
