package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "EditIngredienteDto", description = "Datos para crear un ingrediente")
public record EditIngredienteDto(

        @NotBlank(message = "{editIngredienteDto.nombre.notBlank}")
        @Size(max = 100, message = "{editIngredienteDto.nombre.size}")
        @Schema(description = "Nombre único del ingrediente", example = "Harina")
        String nombre
) {

    public Ingrediente to() {
        return Ingrediente.builder()
                .nombre(nombre)
                .build();
    }
}