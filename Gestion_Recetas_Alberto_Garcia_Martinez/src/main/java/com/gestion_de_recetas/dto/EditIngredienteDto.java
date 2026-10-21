package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EditIngredienteDto", description = "Datos para crear un ingrediente")
public record EditIngredienteDto(

        @Schema(description = "Nombre único del ingrediente", example = "Harina")
        String nombre
) {

    public Ingrediente to() {
        return Ingrediente.builder()
                .nombre(nombre)
                .build();
    }
}