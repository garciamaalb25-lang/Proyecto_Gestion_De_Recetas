package com.gestion_de_recetas.service;

import com.gestion_de_recetas.dto.EditRecetaDto;
import com.gestion_de_recetas.error.notfound.RecetaNoEncontradaException;
import com.gestion_de_recetas.model.Categoria;
import com.gestion_de_recetas.model.Receta;
import com.gestion_de_recetas.repo.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final CategoriaService categoriaService;

    public List<Receta> getAll() {
        return recetaRepository.findAll();
    }

    public List<Receta> getByCategoria(Long categoriaId) {
        // Si la categoría no existe, lanza CategoriaNoEncontradaException (404)
        categoriaService.getById(categoriaId);
        return recetaRepository.findByCategoriaId(categoriaId);
    }

    public Receta getById(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new RecetaNoEncontradaException(id));
    }

    public Receta add(EditRecetaDto dto) {
        // La categoría es obligatoria y debe existir
        Categoria categoria = categoriaService.getById(dto.categoriaId());
        Receta receta = dto.to();
        receta.setCategoria(categoria);
        return recetaRepository.save(receta);
    }

    public Receta update(Long id, EditRecetaDto dto) {
        Receta receta = getById(id);
        Categoria categoria = categoriaService.getById(dto.categoriaId());
        receta.setNombre(dto.nombre());
        receta.setTiempoPreparacionMin(dto.tiempoPreparacionMin());
        receta.setDificultad(dto.dificultad());
        receta.setCategoria(categoria);
        return recetaRepository.save(receta);
    }

    public void delete(Long id) {
        recetaRepository.delete(getById(id));
    }
}