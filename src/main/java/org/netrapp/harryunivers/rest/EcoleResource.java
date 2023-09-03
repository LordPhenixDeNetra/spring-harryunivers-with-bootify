package org.netrapp.harryunivers.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.netrapp.harryunivers.model.EcoleDTO;
import org.netrapp.harryunivers.service.EcoleService;
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
@RequestMapping(value = "/api/ecoles", produces = MediaType.APPLICATION_JSON_VALUE)
public class EcoleResource {

    private final EcoleService ecoleService;

    public EcoleResource(final EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    @GetMapping
    public ResponseEntity<List<EcoleDTO>> getAllEcoles() {
        return ResponseEntity.ok(ecoleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcoleDTO> getEcole(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(ecoleService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEcole(@RequestBody @Valid final EcoleDTO ecoleDTO) {
        final Long createdId = ecoleService.create(ecoleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEcole(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EcoleDTO ecoleDTO) {
        ecoleService.update(id, ecoleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEcole(@PathVariable(name = "id") final Long id) {
        ecoleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
