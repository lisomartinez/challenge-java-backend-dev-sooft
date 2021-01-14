package ar.edu.teclab.prueba.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class DirectorValidEmailTest {
    private String email;

    public DirectorValidEmailTest(String email) {
        this.email = email;
    }

    @Test
    public void canCreateDirectorWithValidEmails() {
        String id = "fb490128-b647-47ff-8260-4eb0cc4be05e";
        Director director = Director.create(id, "Juan", "Perez", email);
        assertThat(director.getEmail()).isEqualTo(email);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"alice@example.com"},
                {"alice@example.co.in"},
                {"alice.bob@example.com"},
                {"alice_bob@example.com"},
                {"alice@example.company.in"}
        });
    }
}
