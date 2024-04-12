package com.sorveteria.alimentos.dto;

import com.sorveteria.alimentos.model.Tipo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AlimentoDto {

    private Long id;
    private Tipo tipo;
    private BigDecimal valor;
    private String nome;
    private boolean status;

}
