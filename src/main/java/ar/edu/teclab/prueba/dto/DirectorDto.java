package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDto {
    @UUID(message = "Director Id should have UUID format")
    private String directorId;

    @NotNull
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters")
    private String lastName;

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    public DirectorDto() {
    }

    public DirectorDto(String directorId, String firstName, String lastName, String email) {
        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static DirectorDto from(Director director) {
        return new DirectorDto(director.getDirectorId(),
                               director.getFirstName(),
                               director.getLastName(),
                               director.getEmail());
    }

    public static DirectorDto create(String firstName, String lastName, String email) {
        return new DirectorDto(null, firstName, lastName, email);
    }

    public Director toEntity() {
        return new Director(directorId, firstName, lastName, email);
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectorDto)) return false;
        DirectorDto that = (DirectorDto) o;
        return Objects.equals(getDirectorId(), that.getDirectorId()) && Objects.equals(getFirstName(),
                                                                                       that.getFirstName()) && Objects
                .equals(getLastName(), that.getLastName()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDirectorId(), getFirstName(), getLastName(), getEmail());
    }

    @Override
    public String toString() {
        return "DirectorDto{" +
                "directorId='" + directorId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
