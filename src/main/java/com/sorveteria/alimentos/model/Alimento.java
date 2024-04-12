package com.sorveteria.alimentos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private String nome;

    @NotNull
    private boolean status;
}
