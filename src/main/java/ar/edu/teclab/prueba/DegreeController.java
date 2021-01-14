package ar.edu.teclab.prueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/degrees")
public class DegreeController {
    private DegreeService service;

    @Autowired
    public DegreeController(DegreeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Degree> create(@RequestBody CreateDegreeDto degreeDto) {
        Degree created = service.create(degreeDto);
        return ResponseEntity.created(URI.create("/degrees/" + created.getDegreeId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Degree>> getAll() {
        List<Degree> degrees = service.findAll();
        return ResponseEntity.ok(degrees);
    }

    @DeleteMapping("/{degreeId}")
    public ResponseEntity<Void> delete(@PathVariable String degreeId) {
        service.deleteByDegreeId(degreeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{degreeId}")
    public ResponseEntity<Degree> delete(@PathVariable String degreeId, @RequestBody Degree degree) {
        Degree updatedDegree = service.update(degree);
        return ResponseEntity.ok(updatedDegree);
    }

}
