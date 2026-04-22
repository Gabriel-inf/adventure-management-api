package com.infnet.AT.operacoes.controller;

import com.infnet.AT.operacoes.dto.RankingAventureiroDTO;
import com.infnet.AT.operacoes.dto.RelatorioMissaoMetricaDTO;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.service.RelatorioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/ranking")
    public ResponseEntity<Page<RankingAventureiroDTO>> obterRankingAventureiros(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim,
            @RequestParam(required = false) StatusMissao status,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(relatorioService.obterRankingAventureiros(inicio, fim, status, pageable));
    }

    @GetMapping("/metricas-missoes")
    public ResponseEntity<List<RelatorioMissaoMetricaDTO>> obterMetricasMissoesConcluidas(
            @RequestParam(defaultValue = "CONCLUIDA") StatusMissao status) {
        return ResponseEntity.ok(relatorioService.obterMetricasMissoesConcluidas(status));
    }
}
