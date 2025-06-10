package com.clinic.inv_api.Service;


import com.clinic.inv_api.DTO.ProdutoDTO;
import com.clinic.inv_api.Mapper.ProdutoMapper;
import com.clinic.inv_api.Model.Produto;
import com.clinic.inv_api.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));
    }

    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {
        produtoRepository.findByCodigo(produtoDTO.codigo())
                .ifPresent(p -> { throw new IllegalArgumentException("Já existe um produto com o código: " + produtoDTO.codigo()); });

        Produto produto = produtoMapper.toEntity(produtoDTO);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoSalvo);
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}