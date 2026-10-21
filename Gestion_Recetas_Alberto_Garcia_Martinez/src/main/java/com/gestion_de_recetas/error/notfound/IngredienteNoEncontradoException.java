package com.gestion_de_recetas.error.notfound;

public class IngredienteNoEncontradoException extends EntidadNoEncontradaException {

    public IngredienteNoEncontradoException(Long id) {
        super("No se ha encontrado el ingrediente con id " + id);
    }
}