package com.clinic.inv_api.Repository;


import com.clinic.inv_api.Model.Movimentacao;
import com.clinic.inv_api.Model.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoIdOrderByDataHoraDesc(Long produtoId);

    @Query("SELECT COALESCE(SUM(m.quantidade), 0.0) FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = :tipo")
    Double sumQuantidadeByProdutoIdAndTipo(Long produtoId, TipoMovimentacao tipo);

    // NOVO MÉTODO PARA O RELATÓRIO
    List<Movimentacao> findByDataHoraBetweenOrderByDataHoraAsc(LocalDateTime start, LocalDateTime end);
}