package ar.edu.teclab.prueba.model;

import ar.edu.teclab.prueba.model.exceptions.DomainException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(Parameterized.class)
public class DirectorInvalidEmailTest {
    private String email;

    public DirectorInvalidEmailTest(String email) {
        this.email = email;
    }

    @Test
    public void cannotCreateDirectorWithInvalidEmails() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        assertThatThrownBy(() -> Director.create(id, "Juan", "Perez", email))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("invalid email address format");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {".alice@example.com"},
                {"alice@example.com."},
                {"alice@example.c"},
                {"aa.com"}
        });
    }
}
