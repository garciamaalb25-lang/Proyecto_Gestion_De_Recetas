package com.gestion_de_recetas.repo;

import com.gestion_de_recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByCategoriaId(Long categoriaId);

    List<Receta> findByCategoriaId(Long categoriaId);
}