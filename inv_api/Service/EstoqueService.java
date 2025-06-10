package com.clinic.inv_api.Service;


import com.clinic.inv_api.DTO.EstoqueStatusDTO;
import com.clinic.inv_api.DTO.MovimentacaoDTO;
import com.clinic.inv_api.Mapper.MovimentacaoMapper;
import com.clinic.inv_api.Model.Movimentacao;

import com.clinic.inv_api.Model.Produto;
import com.clinic.inv_api.Model.TipoMovimentacao;
import com.clinic.inv_api.Repository.MovimentacaoRepository;
import com.clinic.inv_api.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    @Transactional
    public MovimentacaoDTO registrarEntrada(MovimentacaoDTO movimentacaoDTO) {
        Produto produto = produtoRepository.findById(movimentacaoDTO.produtoId())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado para dar entrada no estoque."));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(movimentacaoDTO.quantidade());
        movimentacao.setTipo(TipoMovimentacao.ENTRADA);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toDTO(movimentacaoSalva);
    }

    @Transactional
    public MovimentacaoDTO registrarSaida(MovimentacaoDTO movimentacaoDTO) {
        Produto produto = produtoRepository.findById(movimentacaoDTO.produtoId())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado para dar saída do estoque."));

        // Regra de Negócio: Verifica se há estoque suficiente para a saída
        Double estoqueAtual = getEstoqueAtual(produto.getId());
        if (estoqueAtual < movimentacaoDTO.quantidade()) {
            throw new IllegalStateException("Estoque insuficiente. Quantidade atual: " + estoqueAtual);
        }

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(movimentacaoDTO.quantidade());
        movimentacao.setTipo(TipoMovimentacao.SAIDA);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toDTO(movimentacaoSalva);
    }

    public EstoqueStatusDTO getEstoqueStatus(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado."));

        Double estoqueAtual = getEstoqueAtual(produtoId);
        Double valorTotal = estoqueAtual * produto.getPrecoUnitario();

        return new EstoqueStatusDTO(produtoId, produto.getDescricao(), estoqueAtual, valorTotal);
    }

    public List<MovimentacaoDTO> getHistoricoMovimentacoes(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataHoraDesc(produtoId).stream()
                .map(movimentacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método privado para calcular o estoque atual
    private Double getEstoqueAtual(Long produtoId) {
        Double totalEntradas = movimentacaoRepository.sumQuantidadeByProdutoIdAndTipo(produtoId, TipoMovimentacao.ENTRADA);
        Double totalSaidas = movimentacaoRepository.sumQuantidadeByProdutoIdAndTipo(produtoId, TipoMovimentacao.SAIDA);
        return totalEntradas - totalSaidas;
    }
}