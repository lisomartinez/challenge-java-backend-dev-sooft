package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.model.Director;

public class TestObjectFactory {
    public static final String DIRECTOR_ID = "725d1d64-0ac7-4d09-99ee-0a9920453fe3";
    public static final String DEGREE_ID = "d5dac498-7ad7-4bf7-b94b-c3b283311242";
    Degree createDegree() {
        return Degree.createDegree(
                DEGREE_ID,
                "degree title",
                DegreeType.ONLINE,
                Director.create(DIRECTOR_ID,
                                "Juan",
                                "Perez",
                                "juanperez@gmail.com"));
    }

    CreateDegreeDto createCreateDegreeDto() {
        return new CreateDegreeDto("degree title", "online", DegreeControllerTest.DIRECTOR_ID);
    }

    DegreeDto createDegreeDto() {
        Degree degree = createDegree();
        degree.setDegreeId(DegreeControllerTest.DEGREE_ID);
        return DegreeDto.from(degree);
    }
}
