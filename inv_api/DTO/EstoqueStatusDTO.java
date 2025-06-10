package com.clinic.inv_api.DTO;

public record EstoqueStatusDTO(
        Long produtoId,
        String produtoDescricao,
        Double quantidadeEmEstoque,
        Double valorTotalEmEstoque
) {}