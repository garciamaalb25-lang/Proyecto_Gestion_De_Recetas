package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.AddIngredienteDto;
import com.gestion_de_recetas.dto.EditRecetaDto;
import com.gestion_de_recetas.dto.GetRecetaDetailDto;
import com.gestion_de_recetas.dto.GetRecetaListDto;
import com.gestion_de_recetas.service.RecetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
@Tag(name = "Recetas", description = "Gestión de las recetas y de sus ingredientes")
public class RecetaController {

    private final RecetaService recetaService;

    @Operation(
            summary = "Listar recetas",
            description = "Devuelve todas las recetas, sin sus ingredientes. Si no hay ninguna, devuelve una lista vacía.")
    @ApiResponse(responseCode = "200", description = "Listado de recetas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GetRecetaListDto.class)),
                    examples = @ExampleObject(value = """
                            [
                              { "id": 1, "nombre": "Tarta de queso", "tiempoPreparacionMin": 60, "dificultad": "MEDIA", "categoriaNombre": "Postres" },
                              { "id": 2, "nombre": "Flan de huevo", "tiempoPreparacionMin": 45, "dificultad": "FACIL", "categoriaNombre": "Postres" }
                            ]
                            """)))
    @GetMapping
    public ResponseEntity<List<GetRecetaListDto>> getAll() {
        return ResponseEntity.ok(
                recetaService.getAll().stream().map(GetRecetaListDto::of).toList());
    }

    @Operation(
            summary = "Listar las recetas de una categoría",
            description = "Devuelve las recetas que pertenecen a la categoría indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recetas de la categoría",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetRecetaListDto.class)),
                            examples = @ExampleObject(value = """
                                    [
                                      { "id": 1, "nombre": "Tarta de queso", "tiempoPreparacionMin": 60, "dificultad": "MEDIA", "categoriaNombre": "Postres" }
                                    ]
                                    """))),
            @ApiResponse(responseCode = "404", description = "No existe una categoría con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se ha encontrado la categoría con id 999",
                                      "instance": "/api/v1/recetas/categoria/999"
                                    }
                                    """)))
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<GetRecetaListDto>> getByCategoria(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long categoriaId) {
        return ResponseEntity.ok(
                recetaService.getByCategoria(categoriaId).stream().map(GetRecetaListDto::of).toList());
    }

    @Operation(
            summary = "Obtener una receta",
            description = "Devuelve el detalle de una receta, incluida la lista de sus ingredientes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Tarta de queso",
                                      "tiempoPreparacionMin": 60,
                                      "dificultad": "MEDIA",
                                      "categoriaId": 1,
                                      "categoriaNombre": "Postres",
                                      "ingredientes": [
                                        { "id": 2, "nombre": "Huevo" },
                                        { "id": 3, "nombre": "Azúcar" },
                                        { "id": 4, "nombre": "Queso crema" }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No existe una receta con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se ha encontrado la receta con id 999",
                                      "instance": "/api/v1/recetas/999"
                                    }
                                    """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> getById(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<GetRecetaDetailDto> add(@Valid @RequestBody EditRecetaDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(recetaService.add(dto)));
    }

    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(@PathVariable Long recetaId,
                                                             @Valid @RequestBody AddIngredienteDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(
                        recetaService.addIngrediente(recetaId, dto.ingredienteId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody EditRecetaDto dto) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.update(id, dto)));
    }

    @Operation(
            summary = "Borrar una receta",
            description = "Borra una receta. Sus ingredientes no se borran, solo deja de estar asociada a ellos.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receta borrada"),
            @ApiResponse(responseCode = "404", description = "No existe una receta con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se ha encontrado la receta con id 999",
                                      "instance": "/api/v1/recetas/999"
                                    }
                                    """)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id) {
        recetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}