package com.clinic.inv_api.DTO;


import com.clinic.inv_api.Model.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record MovimentacaoDTO(
        Long id,

        @NotNull(message = "O ID do produto é obrigatório.")
        Long produtoId,

        // Campos para resposta, não para entrada
        String produtoCodigo,
        String produtoDescricao,

        @NotNull(message = "O tipo de movimentação é obrigatório.")
        TipoMovimentacao tipo,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser um número positivo.")
        Double quantidade,

        // Campo para resposta
        LocalDateTime dataHora
) {
    // Construtor compacto para simplificar a criação de uma requisição de entrada
    public MovimentacaoDTO(Long produtoId, TipoMovimentacao tipo, Double quantidade) {
        this(null, produtoId, null, null, tipo, quantidade, null);
    }
}