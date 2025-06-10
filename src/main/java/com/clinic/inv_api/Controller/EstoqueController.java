package com.clinic.inv_api.Controller;


import com.clinic.inv_api.DTO.EstoqueStatusDTO;
import com.clinic.inv_api.DTO.MovimentacaoDTO;
import com.clinic.inv_api.Service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoDTO> registrarEntrada(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        return ResponseEntity.ok(estoqueService.registrarEntrada(movimentacaoDTO));
    }

    @PostMapping("/saida")
    public ResponseEntity<MovimentacaoDTO> registrarSaida(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        return ResponseEntity.ok(estoqueService.registrarSaida(movimentacaoDTO));
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<EstoqueStatusDTO> getEstoqueStatus(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.getEstoqueStatus(produtoId));
    }

    @GetMapping("/movimentacoes/{produtoId}")
    public ResponseEntity<List<MovimentacaoDTO>> getHistoricoMovimentacoes(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.getHistoricoMovimentacoes(produtoId));
    }
}