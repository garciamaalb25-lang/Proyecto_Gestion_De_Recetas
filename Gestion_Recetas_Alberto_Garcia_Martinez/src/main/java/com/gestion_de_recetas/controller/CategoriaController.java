package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.EditCategoriaDto;
import com.gestion_de_recetas.dto.GetCategoriaDto;
import com.gestion_de_recetas.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<GetCategoriaDto>> getAll() {
        return ResponseEntity.ok(
                categoriaService.getAll().stream().map(GetCategoriaDto::of).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<GetCategoriaDto> add(@RequestBody EditCategoriaDto dto) {
        return ResponseEntity.status(201)
                .body(GetCategoriaDto.of(categoriaService.add(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> update(@PathVariable Long id,
                                                  @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}