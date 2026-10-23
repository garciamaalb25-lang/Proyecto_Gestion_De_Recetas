package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCategoriaDto", description = "Categoría de recetas")
public record GetCategoriaDto(

        @Schema(description = "Identificador único de la categoría", example = "1")
        Long id,

        @Schema(description = "Nombre de la categoría", example = "Postres")
        String nombre,

        @Schema(description = "Descripción de la categoría", example = "Dulces y repostería")
        String descripcion
) {

    public static GetCategoriaDto of(Categoria c) {
        return new GetCategoriaDto(c.getId(), c.getNombre(), c.getDescripcion());
    }
}