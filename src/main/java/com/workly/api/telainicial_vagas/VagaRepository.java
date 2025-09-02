package com.workly.api.telainicial_vagas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workly.api.curriculo.vagas;

public interface VagaRepository extends JpaRepository<vagas, Integer> {
}