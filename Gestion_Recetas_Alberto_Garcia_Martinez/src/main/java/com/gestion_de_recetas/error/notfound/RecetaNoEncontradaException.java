package com.gestion_de_recetas.error.notfound;

public class RecetaNoEncontradaException extends EntidadNoEncontradaException {

    public RecetaNoEncontradaException(Long id) {
        super("No se ha encontrado la receta con id " + id);
    }
}