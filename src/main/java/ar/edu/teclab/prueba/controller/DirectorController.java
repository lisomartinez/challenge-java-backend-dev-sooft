package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.dto.DirectorDto;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/degrees/directors")
public class DirectorController {

    private DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getById(@PathVariable String id) {
        Director director = directorService.findByDirectorId(id);
        return ResponseEntity.ok(DirectorDto.from(director));
    }

    @PutMapping
    public ResponseEntity<DirectorDto> update(@RequestBody DirectorDto director) {
        Director updatedDirector = directorService.update(director.toEntity());
        return ResponseEntity.ok(DirectorDto.from(updatedDirector));
    }

    @PostMapping
    public ResponseEntity<DirectorDto> create(@RequestBody DirectorDto directorDto) {
        Director createdDirector = directorService.create(directorDto.toEntity());
        return ResponseEntity.ok(DirectorDto.from(createdDirector));
    }

    @GetMapping
    public ResponseEntity<List<DirectorDto>> getAll() {
        List<Director> directors = directorService.findAll();
        return ResponseEntity.ok(directors.stream().map(DirectorDto::from).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        directorService.deleteByDirectorId(id);
        return ResponseEntity.noContent().build();
    }
}
