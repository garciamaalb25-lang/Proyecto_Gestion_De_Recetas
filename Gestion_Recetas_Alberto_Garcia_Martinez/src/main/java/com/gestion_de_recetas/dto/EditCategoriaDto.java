package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "EditCategoriaDto", description = "Datos para crear o modificar una categoría")
public record EditCategoriaDto(

        @NotBlank(message = "{editCategoriaDto.nombre.notBlank}")
        @Size(max = 100, message = "{editCategoriaDto.nombre.size}")
        @Schema(description = "Nombre único de la categoría", example = "Postres")
        String nombre,

        @Size(max = 255, message = "{editCategoriaDto.descripcion.size}")
        @Schema(description = "Descripción de la categoría", example = "Dulces y repostería")
        String descripcion
) {

    public Categoria to() {
        return Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();
    }
}