package org.netrapp.harryunivers.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.netrapp.harryunivers.model.PersonneDTO;
import org.netrapp.harryunivers.service.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/personnes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneResource {

    private final PersonneService personneService;

    public PersonneResource(final PersonneService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        return ResponseEntity.ok(personneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getPersonne(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(personneService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPersonne(@RequestBody @Valid final PersonneDTO personneDTO) {
        final Long createdId = personneService.create(personneDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePersonne(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PersonneDTO personneDTO) {
        personneService.update(id, personneDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePersonne(@PathVariable(name = "id") final Long id) {
        personneService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
