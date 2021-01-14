package ar.edu.teclab.prueba;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Director {
    private static final Pattern emailValidator =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final String directorId;
    private final String firstName;
    private final String lastName;
    private final String email;

    private Director(String directorId, String firstName, String lastName, String email) {

        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static Director create(String directorId, String firstName, String lastName, String email) {
        assertThatIdHasCorrectFormat(directorId);
        assertThatFieldIsPresent(firstName, "Cannot create a director without first name");
        assertThatFieldIsPresent(lastName, "Cannot create a director without last name");
        assertThatEmailHasCorrectFormat(email);
        return new Director(directorId, firstName, lastName, email);
    }

    private static void assertThatEmailHasCorrectFormat(String email) {
        assertThatFieldIsPresent(email, "Cannot create a Director without email");
        if (!emailValidator.matcher(email).matches()) {
            throw new DomainException("invalid email address format");
        }
    }

    private static void assertThatFieldIsPresent(String field, String message) {
        if (field == null || field.trim().isEmpty())
            throw new DomainException(message);

    }

    private static void assertThatIdHasCorrectFormat(String directorId) {
        assertThatFieldIsPresent(directorId, "Cannot create a director without id");
        try {
            UUID.fromString(directorId);
        } catch (IllegalArgumentException e) {
            throw new DomainException("Director's id should have UUID format");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        return directorId.equals(director.directorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorId);
    }

    public String getEmail() {
        return email;
    }
}
