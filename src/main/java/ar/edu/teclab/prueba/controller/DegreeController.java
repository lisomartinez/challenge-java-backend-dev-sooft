package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.service.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/degrees")
public class DegreeController {
    private DegreeService service;

    @Autowired
    public DegreeController(DegreeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DegreeDto> create(@RequestBody CreateDegreeDto degreeDto) {
        Degree created = service.create(degreeDto);
        return ResponseEntity.created(URI.create("/degrees/" + created.getDegreeId())).body(DegreeDto.from(created));
    }

    @GetMapping("/{degreeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DegreeDto> getById(@PathVariable String degreeId) {
        Degree degree = service.findById(degreeId);
        return ResponseEntity.ok(DegreeDto.from(degree));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DegreeDto>> getAll() {
        List<DegreeDto> degrees = service.findAll().stream().map(DegreeDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(degrees);
    }


    @DeleteMapping("/{degreeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public ResponseEntity<Void> delete(@PathVariable String degreeId) {
        service.deleteByDegreeId(degreeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{degreeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DegreeDto> delete(@PathVariable String degreeId, @RequestBody DegreeDto degree) {

        Degree updatedDegree = service.update(degree.toEntity());
        DegreeDto from = DegreeDto.from(updatedDegree);
        return ResponseEntity.ok(from);
    }

}
