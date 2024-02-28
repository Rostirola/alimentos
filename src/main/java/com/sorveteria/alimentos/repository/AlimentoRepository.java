package com.sorveteria.alimentos.repository;

import com.sorveteria.alimentos.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
