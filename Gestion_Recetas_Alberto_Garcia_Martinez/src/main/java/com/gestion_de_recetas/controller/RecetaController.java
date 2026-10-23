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

    @Operation(
            summary = "Crear una receta",
            description = "Crea una receta nueva. Exige el id de una categoría existente, un nombre único "
                    + "y un tiempo de preparación mayor que 0.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receta creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 4,
                                      "nombre": "Natillas caseras",
                                      "tiempoPreparacionMin": 30,
                                      "dificultad": "FACIL",
                                      "categoriaId": 1,
                                      "categoriaNombre": "Postres",
                                      "ingredientes": []
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos: campos erróneos, nombre duplicado o tiempo menor o igual a 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(name = "Error de validación", value = """
                                            {
                                              "type": "https://example.com/errors/validacion",
                                              "title": "Error de validación",
                                              "status": 400,
                                              "detail": "La petición contiene campos inválidos",
                                              "instance": "/api/v1/recetas",
                                              "invalid-params": [
                                                {
                                                  "object": "editRecetaDto",
                                                  "message": "Ya existe una receta con ese nombre",
                                                  "field": "nombre",
                                                  "rejectedValue": "Tarta de queso"
                                                }
                                              ]
                                            }
                                            """),
                                    @ExampleObject(name = "Tiempo inválido", value = """
                                            {
                                              "type": "https://example.com/errors/tiempo-invalido",
                                              "title": "Tiempo de preparación inválido",
                                              "status": 400,
                                              "detail": "El tiempo de preparación debe ser mayor que 0 (valor recibido: 0)",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """)
                            })),
            @ApiResponse(responseCode = "404", description = "No existe la categoría indicada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se ha encontrado la categoría con id 999",
                                      "instance": "/api/v1/recetas"
                                    }
                                    """)))
    })
    @PostMapping
    public ResponseEntity<GetRecetaDetailDto> add(@Valid @RequestBody EditRecetaDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(recetaService.add(dto)));
    }

    @Operation(
            summary = "Añadir un ingrediente a una receta",
            description = "Asocia un ingrediente existente a una receta existente (relación muchos a muchos). "
                    + "Devuelve la receta con su lista de ingredientes actualizada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente añadido a la receta",
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
                                        { "id": 1, "nombre": "Harina" },
                                        { "id": 2, "nombre": "Huevo" },
                                        { "id": 3, "nombre": "Azúcar" },
                                        { "id": 4, "nombre": "Queso crema" }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Falta el id del ingrediente",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/validacion",
                                      "title": "Error de validación",
                                      "status": 400,
                                      "detail": "La petición contiene campos inválidos",
                                      "instance": "/api/v1/recetas/1/ingredientes",
                                      "invalid-params": [
                                        {
                                          "object": "addIngredienteDto",
                                          "message": "El id del ingrediente es obligatorio",
                                          "field": "ingredienteId"
                                        }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No existe la receta o el ingrediente",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se ha encontrado el ingrediente con id 999",
                                      "instance": "/api/v1/recetas/1/ingredientes"
                                    }
                                    """))),
            @ApiResponse(responseCode = "409", description = "El ingrediente ya está añadido a la receta",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "https://example.com/errors/ingrediente-ya-anadido",
                                      "title": "Ingrediente ya añadido",
                                      "status": 409,
                                      "detail": "El ingrediente con id 2 ya está añadido a la receta con id 1",
                                      "instance": "/api/v1/recetas/1/ingredientes"
                                    }
                                    """)))
    })
    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long recetaId,
            @Valid @RequestBody AddIngredienteDto dto) {
        return ResponseEntity.status(201)
                .body(GetRecetaDetailDto.of(
                        recetaService.addIngrediente(recetaId, dto.ingredienteId())));
    }

    @Operation(
            summary = "Modificar una receta",
            description = "Sustituye los datos de una receta existente. Puede conservar su propio nombre, "
                    + "pero no usar el de otra receta. No modifica sus ingredientes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta modificada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Tarta de queso al horno",
                                      "tiempoPreparacionMin": 70,
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
            @ApiResponse(responseCode = "400", description = "Datos inválidos: campos erróneos, nombre de otra receta o tiempo menor o igual a 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(name = "Error de validación", value = """
                                            {
                                              "type": "https://example.com/errors/validacion",
                                              "title": "Error de validación",
                                              "status": 400,
                                              "detail": "La petición contiene campos inválidos",
                                              "instance": "/api/v1/recetas/1",
                                              "invalid-params": [
                                                {
                                                  "object": "editRecetaDto",
                                                  "message": "Ya existe una receta con ese nombre",
                                                  "field": "nombre",
                                                  "rejectedValue": "Flan de huevo"
                                                }
                                              ]
                                            }
                                            """),
                                    @ExampleObject(name = "Tiempo inválido", value = """
                                            {
                                              "type": "https://example.com/errors/tiempo-invalido",
                                              "title": "Tiempo de preparación inválido",
                                              "status": 400,
                                              "detail": "El tiempo de preparación debe ser mayor que 0 (valor recibido: -5)",
                                              "instance": "/api/v1/recetas/1"
                                            }
                                            """)
                            })),
            @ApiResponse(responseCode = "404", description = "No existe la receta o la categoría indicada",
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
    @PutMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> update(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id,
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