package com.sorveteria.alimentos.controller;

import com.sorveteria.alimentos.dto.AlimentoDto;
import com.sorveteria.alimentos.service.AlimentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/Alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping
    public Page<AlimentoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return alimentoService.obterTodos(paginacao);
    };

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDto> detalhes(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(alimentoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlimentoDto> cadastrar(@RequestBody @Valid AlimentoDto dto, UriComponentsBuilder uriBuilder) {
        AlimentoDto cliente = alimentoService.criaAlimento(dto);
        URI endereco = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(endereco).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid AlimentoDto dto) {
        return ResponseEntity.ok(alimentoService.atualizaAlimento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlimentoDto> remover(@PathVariable @NotNull Long id) {
        alimentoService.excluirAlimento(id);;
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/porta")
    public String retornaPorta(@Value("${local.server.port}") String porta) {
        return String.format("Requisição respondida pela instância executando na porta %s", porta);
    }
}
