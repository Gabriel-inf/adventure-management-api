package com.infnet.AT.operacoes.controller;

import com.infnet.AT.operacoes.dto.AventureiroPerfilCompletoDTO;
import com.infnet.AT.operacoes.dto.AventureiroResumoDTO;
import com.infnet.AT.operacoes.entity.Enum.AventureiroClasse;
import com.infnet.AT.operacoes.service.AventureiroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    @GetMapping
    public ResponseEntity<Page<AventureiroResumoDTO>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(aventureiroService.buscarPorNome(nome, pageable));
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<AventureiroResumoDTO>> buscarComFiltros(
            @RequestParam(required = false, defaultValue = "true") Boolean ativo,
            @RequestParam(required = false) AventureiroClasse classe,
            @RequestParam(required = false, defaultValue = "1") Integer nivel,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(aventureiroService.buscarComFiltros(ativo, classe, nivel, pageable));
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<AventureiroPerfilCompletoDTO> buscarPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(aventureiroService.buscarPerfilCompleto(id));
    }
}
