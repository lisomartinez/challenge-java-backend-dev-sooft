package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.controller.exceptions.ErrorMessage;
import ar.edu.teclab.prueba.dto.DirectorDto;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.service.degree.DirectorService;
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
@RequestMapping(DirectorController.DIRECTORS)
public class DirectorController {

    public static final String DIRECTORS = "/degrees/directors";
    public static final String ID = "/{id}";
    private DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @Operation(summary = "Create a Director")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Director created"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DirectorDto> create(@Valid @RequestBody DirectorDto directorDto) {
        Director createdDirector = directorService.create(directorDto.toEntity());
        return ResponseEntity.created(createUri(createdDirector)).body(DirectorDto.from(createdDirector));
    }

    private URI createUri(Director created) {
        return URI.create("/degrees/directors/" + created.getDirectorId());
    }

    @Operation(summary = "Retrieve a Director")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Director Found"),
            @ApiResponse(responseCode = "404", description = "Director not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @GetMapping(ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DirectorDto> getById(@PathVariable String id) {
        Director director = directorService.findByDirectorId(id);
        return ResponseEntity.ok(DirectorDto.from(director));
    }

    @Operation(summary = "List Directors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Directors"),
    })
    @GetMapping
    public ResponseEntity<List<DirectorDto>> getAll() {
        List<Director> directors = directorService.findAll();
        return ResponseEntity.ok(directors.stream().map(DirectorDto::from).collect(Collectors.toList()));
    }

    @Operation(summary = "Update a Director")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Director updated"),
            @ApiResponse(responseCode = "400", description = "Director not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DirectorDto> update(@Valid @RequestBody DirectorDto director) {
        Director updatedDirector = directorService.update(director.toEntity());
        return ResponseEntity.ok(DirectorDto.from(updatedDirector));
    }

    @Operation(summary = "Delete a Director")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Director deleted"),
            @ApiResponse(responseCode = "400", description = "Director not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        directorService.deleteByDirectorId(id);
        return ResponseEntity.noContent().build();
    }
}
