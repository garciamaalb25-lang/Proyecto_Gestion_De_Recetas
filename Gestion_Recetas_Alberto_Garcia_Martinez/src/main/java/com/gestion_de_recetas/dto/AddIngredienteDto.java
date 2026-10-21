package com.gestion_de_recetas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AddIngredienteDto", description = "Ingrediente que se añade a una receta")
public record AddIngredienteDto(

        @Schema(description = "Id de un ingrediente existente", example = "1")
        Long ingredienteId
) {
}