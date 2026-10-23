package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.EditCategoriaDto;
import com.gestion_de_recetas.dto.GetCategoriaDto;
import com.gestion_de_recetas.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de las categorías de recetas")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(
            summary = "Listar categorías",
            description = "Devuelve todas las categorías. Si no hay ninguna, devuelve una lista vacía."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de categorías",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GetCategoriaDto.class)),
                    examples = @ExampleObject(
                            name = "Listado de categorías",
                            value = """
                                    [
                                      { "id": 1, "nombre": "Postres", "descripcion": "Dulces y repostería" },
                                      { "id": 2, "nombre": "Carnes", "descripcion": "Platos con carne" }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<GetCategoriaDto>> getAll() {
        return ResponseEntity.ok(
                categoriaService.getAll().stream().map(GetCategoriaDto::of).toList());
    }

    @Operation(
            summary = "Obtener una categoría",
            description = "Devuelve una categoría a partir de su id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            { "id": 1, "nombre": "Postres", "descripcion": "Dulces y repostería" }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una categoría con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "Recurso no encontrado",
                                              "status": 404,
                                              "detail": "No se ha encontrado la categoría con id 999",
                                              "instance": "/api/v1/categorias/999"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> getById(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.getById(id)));
    }

    @Operation(
            summary = "Crear una categoría",
            description = "Crea una categoría nueva. El nombre es obligatorio."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoría creada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            { "id": 4, "nombre": "Sopas", "descripcion": "Caldos y cremas" }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/validacion",
                                              "title": "Error de validación",
                                              "status": 400,
                                              "detail": "La petición contiene campos inválidos",
                                              "instance": "/api/v1/categorias",
                                              "invalid-params": [
                                                {
                                                  "object": "editCategoriaDto",
                                                  "message": "El nombre de la categoría es obligatorio",
                                                  "field": "nombre",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<GetCategoriaDto> add(@Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.status(201)
                .body(GetCategoriaDto.of(categoriaService.add(dto)));
    }

    @Operation(
            summary = "Modificar una categoría",
            description = "Sustituye el nombre y la descripción de una categoría existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría modificada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            { "id": 1, "nombre": "Postres caseros", "descripcion": "Dulces hechos en casa" }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/validacion",
                                              "title": "Error de validación",
                                              "status": 400,
                                              "detail": "La petición contiene campos inválidos",
                                              "instance": "/api/v1/categorias/1",
                                              "invalid-params": [
                                                {
                                                  "object": "editCategoriaDto",
                                                  "message": "El nombre de la categoría es obligatorio",
                                                  "field": "nombre",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una categoría con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "Recurso no encontrado",
                                              "status": 404,
                                              "detail": "No se ha encontrado la categoría con id 999",
                                              "instance": "/api/v1/categorias/999"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> update(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id,
            @Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.update(id, dto)));
    }

    @Operation(
            summary = "Borrar una categoría",
            description = "Borra una categoría. No se puede borrar si tiene recetas asociadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría borrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una categoría con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "Recurso no encontrado",
                                              "status": 404,
                                              "detail": "No se ha encontrado la categoría con id 999",
                                              "instance": "/api/v1/categorias/999"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "La categoría tiene recetas asociadas",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://example.com/errors/categoria-con-recetas",
                                              "title": "Categoría con recetas asociadas",
                                              "status": 409,
                                              "detail": "No se puede borrar la categoría con id 1 porque tiene recetas asociadas",
                                              "instance": "/api/v1/categorias/1"
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}