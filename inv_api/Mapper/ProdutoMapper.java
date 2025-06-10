package com.clinic.inv_api.Mapper;


import com.clinic.inv_api.DTO.ProdutoDTO;
import com.clinic.inv_api.Model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.id());
        produto.setCodigo(dto.codigo());
        produto.setDescricao(dto.descricao());
        produto.setPrecoUnitario(dto.precoUnitario());
        produto.setUnidadeMedida(dto.unidadeMedida());
        return produto;
    }

    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPrecoUnitario(),
                produto.getUnidadeMedida()
        );
    }
}
