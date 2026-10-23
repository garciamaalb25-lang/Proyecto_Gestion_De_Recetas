package com.gestion_de_recetas.controller;

import com.gestion_de_recetas.dto.EditIngredienteDto;
import com.gestion_de_recetas.dto.GetIngredienteDto;
import com.gestion_de_recetas.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/ingredientes")
@RequiredArgsConstructor
@Tag(name = "Ingredientes", description = "Gestión de los ingredientes (crear y listar)")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @Operation(
            summary = "Listar ingredientes",
            description = "Devuelve todos los ingredientes. Si no hay ninguno, devuelve una lista vacía."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de ingredientes",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GetIngredienteDto.class)),
                    examples = @ExampleObject(
                            name = "Listado de ingredientes",
                            value = """
                                    [
                                      { "id": 1, "nombre": "Harina" },
                                      { "id": 2, "nombre": "Huevo" },
                                      { "id": 3, "nombre": "Azúcar" }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<GetIngredienteDto>> getAll() {
        return ResponseEntity.ok(
                ingredienteService.getAll().stream().map(GetIngredienteDto::of).toList());
    }

    @Operation(
            summary = "Crear un ingrediente",
            description = "Crea un ingrediente nuevo. El nombre es obligatorio."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ingrediente creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetIngredienteDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            { "id": 6, "nombre": "Mantequilla" }
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
                                              "instance": "/api/v1/ingredientes",
                                              "invalid-params": [
                                                {
                                                  "object": "editIngredienteDto",
                                                  "message": "El nombre del ingrediente es obligatorio",
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
    public ResponseEntity<GetIngredienteDto> add(@Valid @RequestBody EditIngredienteDto dto) {
        return ResponseEntity.status(201)
                .body(GetIngredienteDto.of(ingredienteService.add(dto)));
    }
}   