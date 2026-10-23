package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetIngredienteDto", description = "Ingrediente de una receta")
public record GetIngredienteDto(

        @Schema(description = "Identificador único del ingrediente", example = "1")
        Long id,

        @Schema(description = "Nombre del ingrediente", example = "Harina")
        String nombre
) {

    public static GetIngredienteDto of(Ingrediente i) {
        return new GetIngredienteDto(i.getId(), i.getNombre());
    }
}