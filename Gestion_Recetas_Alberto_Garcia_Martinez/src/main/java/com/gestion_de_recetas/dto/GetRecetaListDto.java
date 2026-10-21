package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Dificultad;
import com.gestion_de_recetas.model.Receta;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetRecetaListDto", description = "Elemento de un listado de recetas")
public record GetRecetaListDto(

        @Schema(description = "Identificador único de la receta", example = "1")
        Long id,

        @Schema(description = "Nombre de la receta", example = "Tarta de queso")
        String nombre,

        @Schema(description = "Tiempo de preparación en minutos", example = "60")
        Integer tiempoPreparacionMin,

        @Schema(description = "Dificultad de la receta", example = "MEDIA")
        Dificultad dificultad,

        @Schema(description = "Nombre de la categoría", example = "Postres")
        String categoriaNombre
) {

    public static GetRecetaListDto of(Receta r) {
        return new GetRecetaListDto(
                r.getId(),
                r.getNombre(),
                r.getTiempoPreparacionMin(),
                r.getDificultad(),
                r.getCategoria() != null ? r.getCategoria().getNombre() : null
        );
    }
}