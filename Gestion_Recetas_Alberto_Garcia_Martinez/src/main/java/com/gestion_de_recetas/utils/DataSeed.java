package com.gestion_de_recetas.utils;

import com.gestion_de_recetas.model.Categoria;
import com.gestion_de_recetas.repo.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final CategoriaRepository categoriaRepository;

    @PostConstruct
    public void initData() {
        categoriaRepository.saveAll(List.of(
                Categoria.builder().nombre("Postres").descripcion("Dulces y repostería").build(),
                Categoria.builder().nombre("Carnes").descripcion("Platos con carne").build(),
                Categoria.builder().nombre("Ensaladas").descripcion("Platos fríos y ligeros").build()
        ));
    }
}