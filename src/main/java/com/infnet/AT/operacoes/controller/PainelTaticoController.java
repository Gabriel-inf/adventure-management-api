package com.infnet.AT.operacoes.controller;

import com.infnet.AT.operacoes.entity.PainelTaticoMissao;
import com.infnet.AT.operacoes.service.PainelTaticoMissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoController {

    private final PainelTaticoMissaoService service;

    public PainelTaticoController(PainelTaticoMissaoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> getTopMissões() {
        return ResponseEntity.ok(service.buscarTopRankingTatico());
    }
}
