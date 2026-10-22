package com.gestion_de_recetas.service;

import com.gestion_de_recetas.dto.EditCategoriaDto;
import com.gestion_de_recetas.error.CategoriaConRecetasException;
import com.gestion_de_recetas.error.notfound.CategoriaNoEncontradaException;
import com.gestion_de_recetas.model.Categoria;
import com.gestion_de_recetas.repo.CategoriaRepository;
import com.gestion_de_recetas.repo.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final RecetaRepository recetaRepository;

    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    public Categoria getById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));
    }

    public Categoria add(EditCategoriaDto dto) {
        return categoriaRepository.save(dto.to());
    }

    public Categoria update(Long id, EditCategoriaDto dto) {
        Categoria categoria = getById(id);
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return categoriaRepository.save(categoria);
    }

    public void delete(Long id) {
        Categoria categoria = getById(id);
        if (recetaRepository.existsByCategoriaId(id)) {
            throw new CategoriaConRecetasException(id);
        }
        categoriaRepository.delete(categoria);
    }
}