package com.clinic.inv_api.Mapper;


import com.clinic.inv_api.DTO.MovimentacaoDTO;
import com.clinic.inv_api.Model.Movimentacao;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoMapper {

    public MovimentacaoDTO toDTO(Movimentacao movimentacao) {
        return new MovimentacaoDTO(
                movimentacao.getId(),
                movimentacao.getProduto().getId(),
                movimentacao.getProduto().getCodigo(),
                movimentacao.getProduto().getDescricao(),
                movimentacao.getTipo(),
                movimentacao.getQuantidade(),
                movimentacao.getDataHora()
        );
    }
}