package ar.edu.teclab.prueba.utils;

import ar.edu.teclab.prueba.controllers.DegreeControllerTest;
import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.dto.DirectorDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.model.Director;

import java.util.ArrayList;
import java.util.HashSet;

public class TestObjectFactory {
    public static final String DIRECTOR_ID = "725d1d64-0ac7-4d09-99ee-0a9920453fe3";
    public static final String DEGREE_ID = "d5dac498-7ad7-4bf7-b94b-c3b283311242";
    public static final String OTHER_DEGREE_ID = "f4a10e03-9cf3-4dd1-8909-68cd01948546";

    public Degree createDegree() {
        return Degree.aDegree()
                     .setDegreeId(DEGREE_ID)
                     .setTitle("degree title")
                     .setType(DegreeType.ONLINE)
                     .setDirector(Director.create(DIRECTOR_ID,
                                                  "Juan",
                                                  "Perez",
                                                  "juanperez@gmail.com"))
                     .setStudyPlan(new HashSet<>())
                     .build();
    }

    public CreateDegreeDto createCreateDegreeDto() {
        return CreateDegreeDto.create("degree title", "online", DegreeControllerTest.DIRECTOR_ID, new ArrayList<>());
    }

    public DegreeDto createDegreeDto() {
        Degree degree = createDegree();
        degree.setDegreeId(DegreeControllerTest.DEGREE_ID);
        return DegreeDto.from(degree);
    }

    public Degree createDegree(Director director) {
        return Degree.aDegree()
                     .setDegreeId(DEGREE_ID)
                     .setTitle("degree title")
                     .setType(DegreeType.ONLINE)
                     .setDirector(director)
                     .setStudyPlan(new HashSet<>())
                     .build();
    }

    public CreateDegreeDto createCreateDegreeDto(String director) {
        return CreateDegreeDto.create("degree title", "online", director, new ArrayList<>());
    }

    public Director createDirector() {
        return Director.create(DIRECTOR_ID,
                               "Juan",
                               "Perez",
                               "juanperez@gmail.com");
    }

    public Director createDirector(String directorId) {
        return Director.create(directorId,
                               "Juan",
                               "Perez",
                               "juanperez@gmail.com");
    }

    public DirectorDto createDirectorDto() {
        return DirectorDto.from(createDirector());
    }
}
