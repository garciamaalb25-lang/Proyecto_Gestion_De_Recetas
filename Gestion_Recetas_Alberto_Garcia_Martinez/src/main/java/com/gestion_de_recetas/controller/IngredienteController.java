package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.EditIngredienteDto;
import com.gestion_de_recetas.dto.GetIngredienteDto;
import com.gestion_de_recetas.service.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredientes")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<GetIngredienteDto>> getAll() {
        return ResponseEntity.ok(
                ingredienteService.getAll().stream().map(GetIngredienteDto::of).toList());
    }

    @PostMapping
    public ResponseEntity<GetIngredienteDto> add(@Valid @RequestBody EditIngredienteDto dto) {
        return ResponseEntity.status(201)
                .body(GetIngredienteDto.of(ingredienteService.add(dto)));
    }
}