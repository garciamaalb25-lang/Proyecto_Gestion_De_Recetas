package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Dificultad;
import com.gestion_de_recetas.model.Receta;
import com.gestion_de_recetas.validation.UniqueRecetaNombre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "EditRecetaDto", description = "Datos para crear o modificar una receta")
public record EditRecetaDto(

        @UniqueRecetaNombre(message = "{editRecetaDto.nombre.uniqueRecetaNombre}")
        @NotBlank(message = "{editRecetaDto.nombre.notBlank}")
        @Size(max = 100, message = "{editRecetaDto.nombre.size}")
        @Schema(description = "Nombre único de la receta", example = "Tarta de queso")
        String nombre,

        @NotNull(message = "{editRecetaDto.tiempoPreparacionMin.notNull}")
        @Schema(description = "Tiempo de preparación en minutos (debe ser mayor que 0)", example = "60")
        Integer tiempoPreparacionMin,

        @NotNull(message = "{editRecetaDto.dificultad.notNull}")
        @Schema(description = "Dificultad de la receta", example = "MEDIA")
        Dificultad dificultad,

        @NotNull(message = "{editRecetaDto.categoriaId.notNull}")
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