package com.clinic.inv_api.DTO;


import com.clinic.inv_api.Model.UnidadeMedida;
import jakarta.validation.constraints.*;

public record ProdutoDTO(
        Long id,

        @NotBlank(message = "O código do produto não pode ser vazio.")
        String codigo,

        @NotBlank(message = "A descrição não pode ser vazia.")
        String descricao,

        @NotNull(message = "O preço unitário é obrigatório.")
        @Positive(message = "O preço unitário deve ser um número positivo.")
        Double precoUnitario,

        @NotNull(message = "A unidade de medida é obrigatória.")
        UnidadeMedida unidadeMedida
) {}

