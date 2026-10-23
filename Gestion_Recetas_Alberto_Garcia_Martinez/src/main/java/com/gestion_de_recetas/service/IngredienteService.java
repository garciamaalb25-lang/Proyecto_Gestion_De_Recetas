package com.gestion_de_recetas.service;

import com.gestion_de_recetas.dto.EditIngredienteDto;
import com.gestion_de_recetas.error.notfound.IngredienteNoEncontradoException;
import com.gestion_de_recetas.model.Ingrediente;
import com.gestion_de_recetas.repo.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public List<Ingrediente> getAll() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente getById(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNoEncontradoException(id));
    }

    public Ingrediente add(EditIngredienteDto dto) {
        return ingredienteRepository.save(dto.to());
    }
}