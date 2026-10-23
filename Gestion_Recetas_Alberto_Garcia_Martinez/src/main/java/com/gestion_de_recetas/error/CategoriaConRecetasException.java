package com.gestion_de_recetas.error;

public class CategoriaConRecetasException extends RuntimeException {

    public CategoriaConRecetasException(Long categoriaId) {
        super("No se puede borrar la categoría con id " + categoriaId
                + " porque tiene recetas asociadas");
    }
}