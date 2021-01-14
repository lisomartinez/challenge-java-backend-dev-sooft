package ar.edu.teclab.prueba.model;


import ar.edu.teclab.prueba.model.exceptions.DomainException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DirectorTest {
    @Test
    public void cannotCreateDirectorWithoutId() {
        assertThatThrownBy(() -> Director.create(" ", "Juan", "Perez", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without id");
    }

    @Test
    public void cannotCreateDirectorWithNullId() {
        assertThatThrownBy(() -> Director.create(null, "Juan", "Perez", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without id");
    }

    @Test
    public void idShouldHaveUUIDFormat() {
        assertThatThrownBy(() -> Director.create("other format", "Juan", "Perez", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Director's id should have UUID format");
    }

    @Test
    public void cannotCreateDirectorWithoutFirstName() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, " ", "Perez", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without first name");
    }

    @Test
    public void cannotCreateDirectorWithNullFirstName() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, null, "Perez", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without first name");
    }

    @Test
    public void cannotCreateDirectorWithoutLastName() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, "Juan", " ", "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without last name");
    }

    @Test
    public void cannotCreateDirectorWithNullLastName() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, "Juan", null, "juanperez@gmail.com"))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a director without last name");
    }

    @Test
    public void cannotCreateDirectorWithoutEmail() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, "Juan", "Perez", " "))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Director without email");
    }

    @Test
    public void cannotCreateDirectorWithNullEmail() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, "Juan", "Perez", " "))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Director without email");
    }


}


