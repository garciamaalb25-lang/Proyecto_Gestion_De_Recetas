package com.gestion_de_recetas.utils;

import com.gestion_de_recetas.model.Categoria;
import com.gestion_de_recetas.model.Dificultad;
import com.gestion_de_recetas.model.Receta;
import com.gestion_de_recetas.repo.CategoriaRepository;
import com.gestion_de_recetas.repo.RecetaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final CategoriaRepository categoriaRepository;
    private final RecetaRepository recetaRepository;

    @PostConstruct
    public void initData() {

        Categoria postres = Categoria.builder()
                .nombre("Postres").descripcion("Dulces y repostería").build();
        Categoria carnes = Categoria.builder()
                .nombre("Carnes").descripcion("Platos con carne").build();
        Categoria ensaladas = Categoria.builder()
                .nombre("Ensaladas").descripcion("Platos fríos y ligeros").build();

        categoriaRepository.saveAll(List.of(postres, carnes, ensaladas));

        recetaRepository.saveAll(List.of(
                Receta.builder()
                        .nombre("Tarta de queso")
                        .tiempoPreparacionMin(60)
                        .dificultad(Dificultad.MEDIA)
                        .categoria(postres)
                        .build(),
                Receta.builder()
                        .nombre("Flan de huevo")
                        .tiempoPreparacionMin(45)
                        .dificultad(Dificultad.FACIL)
                        .categoria(postres)
                        .build(),
                Receta.builder()
                        .nombre("Carrillada al vino tinto")
                        .tiempoPreparacionMin(180)
                        .dificultad(Dificultad.DIFICIL)
                        .categoria(carnes)
                        .build()
        ));
    }
}