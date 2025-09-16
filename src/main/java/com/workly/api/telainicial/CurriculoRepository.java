package com.workly.api.telainicial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.workly.api.telainicial_vagas.curriculo;
import java.util.List;

public interface CurriculoRepository extends JpaRepository<curriculo, Integer> {

    // 🔹 Método para filtrar currículos por usuário, descrição, tipo, curso ou nível
    @Query("SELECT c FROM curriculo c WHERE " +
           "LOWER(c.usuario) LIKE %:filtro% OR " +
           "LOWER(c.descricao) LIKE %:filtro% OR " +
           "LOWER(c.tipo) LIKE %:filtro% OR " +
           "LOWER(c.curso) LIKE %:filtro% OR " +
           "LOWER(c.nivel) LIKE %:filtro%")
    List<curriculo> findByFiltro(@Param("filtro") String filtro);
}
