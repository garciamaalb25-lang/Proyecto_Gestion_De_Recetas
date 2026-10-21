package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.AddIngredienteDto;
import com.gestion_de_recetas.dto.EditRecetaDto;
import com.gestion_de_recetas.dto.GetRecetaDetailDto;
import com.gestion_de_recetas.dto.GetRecetaListDto;
import com.gestion_de_recetas.service.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    @GetMapping
    public ResponseEntity<List<GetRecetaListDto>> getAll() {
        return ResponseEntity.ok(
                recetaService.getAll().stream().map(GetRecetaListDto::of).toList());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<GetRecetaListDto>> getByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(
                recetaService.getByCategoria(categoriaId).stream().map(GetRecetaListDto::of).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<GetRecetaDetailDto> add(@RequestBody EditRecetaDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(recetaService.add(dto)));
    }

    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(@PathVariable Long recetaId,
                                                             @RequestBody AddIngredienteDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(
                        recetaService.addIngrediente(recetaId, dto.ingredienteId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> update(@PathVariable Long id,
                                                     @RequestBody EditRecetaDto dto) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}