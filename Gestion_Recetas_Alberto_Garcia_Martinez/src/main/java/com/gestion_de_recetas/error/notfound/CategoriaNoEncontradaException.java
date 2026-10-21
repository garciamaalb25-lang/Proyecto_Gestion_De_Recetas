package com.gestion_de_recetas.error.notfound;

public class CategoriaNoEncontradaException extends EntidadNoEncontradaException {

    public CategoriaNoEncontradaException(Long id) {
        super("No se ha encontrado la categoría con id " + id);
    }
}