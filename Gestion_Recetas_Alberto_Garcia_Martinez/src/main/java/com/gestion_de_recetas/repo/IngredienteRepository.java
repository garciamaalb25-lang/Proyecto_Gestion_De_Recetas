package com.gestion_de_recetas.repo;

import com.gestion_de_recetas.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    boolean existsByNombre(String nombre);
}