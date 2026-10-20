package com.gestion_de_recetas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;
}