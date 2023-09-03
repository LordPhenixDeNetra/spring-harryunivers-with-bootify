package org.netrapp.harryunivers.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.netrapp.harryunivers.model.MaisonDTO;
import org.netrapp.harryunivers.service.MaisonService;
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
@RequestMapping(value = "/api/maisons", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaisonResource {

    private final MaisonService maisonService;

    public MaisonResource(final MaisonService maisonService) {
        this.maisonService = maisonService;
    }

    @GetMapping
    public ResponseEntity<List<MaisonDTO>> getAllMaisons() {
        return ResponseEntity.ok(maisonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaisonDTO> getMaison(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(maisonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMaison(@RequestBody @Valid final MaisonDTO maisonDTO) {
        final Long createdId = maisonService.create(maisonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMaison(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MaisonDTO maisonDTO) {
        maisonService.update(id, maisonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMaison(@PathVariable(name = "id") final Long id) {
        maisonService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
