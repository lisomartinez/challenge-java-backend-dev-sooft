package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.model.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DegreeTest {

    public static final String ID = "cf82e92c-9216-4967-a931-6f36b3be87da";

    @Test
    public void degreeKnowsItsTitle() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = createDegree(ID, title, type, director, new HashSet<>());
        assertThat(degree.getTitle()).isEqualTo(title);
    }

    private Degree createDegree(String ID


            , String title, DegreeType type, Director director, Set<Subject> studyPlan
    ) {
        return Degree.aDegree()
                     .setDegreeId(ID


                     )
                     .setTitle(title)
                     .setType(type)
                     .setDirector(director)
                     .setStudyPlan(studyPlan)
                     .build();
    }

    @Test
    public void degreeKnowsItsType() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = createDegree(ID


                , title, type, director, new HashSet<>());
        assertThat(degree.getType()).isEqualTo(DegreeType.ONLINE);
    }

    @Test
    public void degreeKnowsItsDirector() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        Degree degree = createDegree(ID


                , title, type, director, new HashSet<>());
        assertThat(degree.getDirector()).isEqualTo(director);
    }

    @Test
    public void cannotCreateADegreeWithoutTitle() {
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree(ID, "   ", type, director, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without title.");
    }

    @Test
    public void cannotCreateADegreeWithNulltitle() {
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree(ID


                , null, type, director, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without title.");
    }

    @Test
    public void cannotCreateADegreeWithoutId() {
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree("  ", "   ", type, director, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without an id.");
    }

    @Test
    public void cannotCreateADegreeWithNullId() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree(null, title, type, director, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without an id.");
    }

    @Test
    public void shouldHaveIdWithUUIDFormat() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree("other format", title, type, director, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Degree Id should have UUID format");
    }


    @Test
    public void cannotCreateADegreeWithNullDirector() {
        String title = "Técnico Superior en Programación";
        DegreeType type = DegreeType.ONLINE;
        Director director =
                Director.create("279a1be9-c4ab-406b-9e74-31045dd01edf", "Juan", "Perez", "juanperez@gmail.com");
        assertThatThrownBy(() -> createDegree(ID, title, type, null, new HashSet<>()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot create a Degree without a Director.");
    }


}
