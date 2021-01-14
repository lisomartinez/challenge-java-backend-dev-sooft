package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.model.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDto {
    private String directorId;
    private String firstName;
    private String lastName;
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

    public Director toEntity() {
        return new Director(directorId, firstName, lastName, email);

    }
}
