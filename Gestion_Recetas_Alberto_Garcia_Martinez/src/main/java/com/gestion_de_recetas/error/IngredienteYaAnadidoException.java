package com.gestion_de_recetas.error;

public class IngredienteYaAnadidoException extends RuntimeException {

    public IngredienteYaAnadidoException(Long recetaId, Long ingredienteId) {
        super("El ingrediente con id " + ingredienteId
                + " ya está añadido a la receta con id " + recetaId);
    }
}