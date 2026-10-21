package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Dificultad;
import com.gestion_de_recetas.model.Receta;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EditRecetaDto", description = "Datos para crear o modificar una receta")
public record EditRecetaDto(

        @Schema(description = "Nombre único de la receta", example = "Tarta de queso")
        String nombre,

        @Schema(description = "Tiempo de preparación en minutos", example = "60")
        Integer tiempoPreparacionMin,

        @Schema(description = "Dificultad de la receta", example = "MEDIA")
        Dificultad dificultad,

        @Schema(description = "Id de una categoría existente", example = "1")
        Long categoriaId
) {

    public Receta to() {
        return Receta.builder()
                .nombre(nombre)
                .tiempoPreparacionMin(tiempoPreparacionMin)
                .dificultad(dificultad)
                .build();
    }
}