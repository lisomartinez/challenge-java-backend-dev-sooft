package ar.edu.teclab.prueba.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class CreateDegreeDto {
    @UUID
    private String directorId;

    @NotNull
    @Size(min = 2, max = 255, message = "Subject title should be between 2 and 255 characters")
    private String title;

    @NotNull
    @Size(min = 2, max = 25, message = "Subject title should be between 2 and 25 characters")
    private String type;

    @NotNull
    private List<SubjectDto> studyPlan;

    private CreateDegreeDto(String title, String type, String directorId, List<SubjectDto> studyPlan) {
        this.title = title;
        this.type = type;
        this.directorId = directorId;
        this.studyPlan = studyPlan;
    }

    public CreateDegreeDto() {
    }

    public static CreateDegreeDto create(String title,
                                         String type,
                                         String directorId,
                                         List<SubjectDto> studyPlan
    ) {
        return new CreateDegreeDto(title, type, directorId, studyPlan);
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

    public List<SubjectDto> getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(List<SubjectDto> studyPlan) {
        this.studyPlan = studyPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateDegreeDto)) return false;
        CreateDegreeDto that = (CreateDegreeDto) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getType(),
                                                                             that.getType()) && Objects
                .equals(getDirectorId(), that.getDirectorId()) && Objects.equals(getStudyPlan(),
                                                                                 that.getStudyPlan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getType(), getDirectorId(), getStudyPlan());
    }

    @Override
    public String toString() {
        return "CreateDegreeDto{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", directorId='" + directorId + '\'' +
                ", studyPlan=" + studyPlan +
                '}';
    }
}
