package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;

import java.util.Locale;
import java.util.Objects;

public class DegreeDto {
    private String degreeId;
    private String title;
    private String type;
    private DirectorDto director;

    public DegreeDto() {
    }

    public DegreeDto(String degreeId, String title, DegreeType type, DirectorDto director) {
        this.degreeId = degreeId;
        this.title = title;
        this.type = type.toString().toLowerCase(Locale.ROOT);
        this.director = director;
    }


    public static DegreeDto from(Degree degree) {
        return new DegreeDto(degree.getDegreeId(),
                             degree.getTitle(),
                             degree.getType(),
                             DirectorDto.from(degree.getDirector()));
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
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

    public DirectorDto getDirector() {
        return director;
    }

    public void setDirector(DirectorDto director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DegreeDto)) return false;
        DegreeDto degreeDto = (DegreeDto) o;
        return Objects.equals(getDegreeId(), degreeDto.getDegreeId()) && Objects.equals(getTitle(),
                                                                                        degreeDto.getTitle()) && getType() == degreeDto
                .getType() && Objects.equals(getDirector(), degreeDto.getDirector());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDegreeId(), getTitle(), getType(), getDirector());
    }

    @Override
    public String toString() {
        return "DegreeDto{" +
                "degreeId='" + degreeId + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", director=" + director +
                '}';
    }

    public Degree toEntity() {
        return new Degree(degreeId, title, DegreeType.valueOf(type.toUpperCase(Locale.ROOT)), director.toEntity());
    }
}
