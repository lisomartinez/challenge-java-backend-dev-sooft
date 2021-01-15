package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Subject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SubjectDto {

    @NotNull
    @Size(min = 2, max = 50, message = "Subject title should be between 2 and 50 characters")
    private String title;

    @Min(1)
    @NotNull
    private int year;

    public SubjectDto() {
    }

    private SubjectDto(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public static SubjectDto from(Subject subject) {
        return new SubjectDto( subject.getTitle(), subject.getYear());
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
        return getYear() == that.getYear()  && getTitle()
                .equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash( getTitle(), getYear());
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }

    public Subject toEntity() {
        return new Subject(title, year);
    }
}
