package com.gestion_de_recetas.dto;

import com.gestion_de_recetas.model.Dificultad;
import com.gestion_de_recetas.model.Receta;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "GetRecetaDetailDto", description = "Detalle de una receta con sus ingredientes")
public record GetRecetaDetailDto(

        @Schema(description = "Identificador único de la receta", example = "1")
        Long id,

        @Schema(description = "Nombre de la receta", example = "Tarta de queso")
        String nombre,

        @Schema(description = "Tiempo de preparación en minutos", example = "60")
        Integer tiempoPreparacionMin,

        @Schema(description = "Dificultad de la receta", example = "MEDIA")
        Dificultad dificultad,

        @Schema(description = "Id de la categoría", example = "1")
        Long categoriaId,

        @Schema(description = "Nombre de la categoría", example = "Postres")
        String categoriaNombre,

        @Schema(description = "Ingredientes de la receta")
        List<GetIngredienteDto> ingredientes
) {

    public static GetRecetaDetailDto of(Receta r) {
        return new GetRecetaDetailDto(
                r.getId(),
                r.getNombre(),
                r.getTiempoPreparacionMin(),
                r.getDificultad(),
                r.getCategoria() != null ? r.getCategoria().getId() : null,
                r.getCategoria() != null ? r.getCategoria().getNombre() : null,
                r.getIngredientes().stream().map(GetIngredienteDto::of).toList()
        );
    }
}