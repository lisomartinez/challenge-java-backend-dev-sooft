package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.model.Subject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DegreeDto {

    @UUID
    private String degreeId;

    @Size(min = 2, max = 255, message = "Degree title should be between 2 and 255 characters")
    private String title;

    @Size(min = 2, max = 25, message = "Degree type should be between 2 and 25 characters")
    private String type;

    @NotNull
    private DirectorDto director;

    @NotNull
    @Valid
    private List<SubjectDto> studyPlan;

    public DegreeDto() {
    }

    private DegreeDto(String degreeId,
                      String title,
                      DegreeType type,
                      DirectorDto director,
                      List<SubjectDto> studyPlan
    ) {
        this.degreeId = degreeId;
        this.title = title;
        this.type = type.toString().toLowerCase(Locale.ROOT);
        this.director = director;
        this.studyPlan = studyPlan;
    }


    public static DegreeDto from(Degree degree) {

        return new DegreeDto(degree.getDegreeId(),
                             degree.getTitle(),
                             degree.getType(),
                             DirectorDto.from(degree.getDirector()),
                             mapStudyPlanToDto(degree));
    }

    private static List<SubjectDto> mapStudyPlanToDto(Degree degree) {
        return degree.getStudyPlan().stream().map(SubjectDto::from).collect(Collectors.toList());
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

    public List<SubjectDto> getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(List<SubjectDto> studyPlan) {
        this.studyPlan = studyPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DegreeDto)) return false;
        DegreeDto degreeDto = (DegreeDto) o;
        return Objects.equals(getDegreeId(), degreeDto.getDegreeId()) && Objects.equals(getTitle(),
                                                                                        degreeDto.getTitle()) && Objects
                .equals(getType(), degreeDto.getType()) && Objects.equals(getDirector(),
                                                                          degreeDto.getDirector()) && Objects
                .equals(getStudyPlan(), degreeDto.getStudyPlan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDegreeId(), getTitle(), getType(), getDirector(), getStudyPlan());
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
        return Degree.aDegree()
                 .setDegreeId(degreeId)
                 .setType(DegreeType.valueOf(type.toUpperCase(Locale.ROOT)))
                     .setDirector(director.toEntity())
                     .setTitle(title)
                     .setStudyPlan(mapStudyPlanToEntity(studyPlan))
                .build();
    }

    private Set<Subject> mapStudyPlanToEntity(List<SubjectDto> studyPlan) {
        return studyPlan.stream().map(SubjectDto::toEntity).collect(Collectors.toSet());
    }
}
