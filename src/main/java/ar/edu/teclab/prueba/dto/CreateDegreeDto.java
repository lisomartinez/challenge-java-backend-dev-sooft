package ar.edu.teclab.prueba.dto;

import java.util.Objects;

public class CreateDegreeDto {
    private String title;
    private String type;
    private String directorId;

    public CreateDegreeDto(String title, String type, String directorId) {
        this.title = title;
        this.type = type;
        this.directorId = directorId;
    }

    public CreateDegreeDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateDegreeDto)) return false;
        CreateDegreeDto that = (CreateDegreeDto) o;
        return getTitle().equals(that.getTitle()) && getType().equals(that.getType()) && getDirectorId().equals(that.getDirectorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getType(), getDirectorId());
    }

    @Override
    public String toString() {
        return "CreateDegreeDto{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", directorId='" + directorId + '\'' +
                '}';
    }
}
