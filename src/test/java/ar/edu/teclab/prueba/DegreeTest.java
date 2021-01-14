package ar.edu.teclab.prueba;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DegreeTest {
    @Test
    public void degreeKnowsItsTitle() {
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = Degree.createDegree(id, title, type, director);
        assertThat(degree.getTitle()).isEqualTo(title);
    }

    @Test
    public void degreeKnowsItsType() {
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = Degree.createDegree(id, title, type, director);
        assertThat(degree.getType()).isEqualTo(DegreeType.ONLINE);
    }

    @Test
    public void degreeKnowsItsDirector() {
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = Degree.createDegree(id, title, type, director);
        assertThat(degree.getDirector()).isEqualTo(director);
    }

    @Test
    public void cannotCreateADegreeWithoutTitle() {
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree(id, "   ", type, director))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without title.");
    }

    @Test
    public void cannotCreateADegreeWithNulltitle() {
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree(id, null, type, director))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without title.");
    }

    @Test
    public void cannotCreateADegreeWithoutId() {
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree("  ", "   ", type, director))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without an id.");
    }

    @Test
    public void cannotCreateADegreeWithNullId() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree(null, title, type, director))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without an id.");
    }

    @Test
    public void shouldHaveIdWithUUIDFormat() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree("other format", title, type, director))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Degree Id should have UUID format");
    }


    @Test
    public void cannotCreateADegreeWithNullDirector() {
        String title = "Técnico Superior en Programación";
        String id = "cf82e92c-9216-4967-a931-6f36b3be87da";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> Degree.createDegree(id, title, type, null))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without a Director.");
    }


}
