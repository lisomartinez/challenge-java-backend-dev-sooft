package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.controller.exceptions.ErrorMessage;
import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.service.degree.DegreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = DegreeController.DEGREES, produces = DegreeController.APPLICATION_JSON)
public class DegreeController {
    public static final String DEGREES = "/degrees";
    public static final String APPLICATION_JSON = "application/json";
    public static final String DEGREE_ID = "/{degreeId}";
    private final DegreeService service;

    @Autowired
    public DegreeController(DegreeService service) {
        this.service = service;
    }

    @Operation(summary = "Create a Degree")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Degree created"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DegreeDto> create(@Valid @RequestBody CreateDegreeDto degreeDto) {
        Degree created = service.create(degreeDto);
        return ResponseEntity.created(createUri(created)).body(DegreeDto.from(created));
    }

    private URI createUri(Degree created) {
        return URI.create("/degrees/" + created.getDegreeId());
    }


    @Operation(summary = "Retrieve a Degree")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Degree Found"),
            @ApiResponse(responseCode = "404", description = "Degree not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @GetMapping(DEGREE_ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DegreeDto> getById(@PathVariable String degreeId) {
        Degree degree = service.findById(degreeId);
        return ResponseEntity.ok(DegreeDto.from(degree));
    }

    @Operation(summary = "List Degrees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Degrees"),
    })
    @GetMapping
    public ResponseEntity<List<DegreeDto>> getAll() {
        List<DegreeDto> degrees = service.findAll().stream().map(DegreeDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(degrees);
    }

    @Operation(summary = "Update a Degree")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Degree updated"),
            @ApiResponse(responseCode = "400", description = "Degree not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DegreeDto> update(@Valid @RequestBody DegreeDto degree) {
        Degree degreeForUpdate = degree.toEntity();
        Degree updatedDegree = service.update(degreeForUpdate);
        DegreeDto from = DegreeDto.from(updatedDegree);
        return ResponseEntity.ok(from);
    }

    @Operation(summary = "Delete a Degree")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Degree deleted"),
            @ApiResponse(responseCode = "400", description = "Degree not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @DeleteMapping(value = DEGREE_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String degreeId) {
        service.deleteByDegreeId(degreeId);
        return ResponseEntity.noContent().build();
    }

}
