package com.infnet.AT.operacoes.controller;

import com.infnet.AT.operacoes.dto.MissaoDetalhadaDTO;
import com.infnet.AT.operacoes.dto.MissaoResumoDTO;
import com.infnet.AT.operacoes.entity.Enum.NivelPerigo;
import com.infnet.AT.operacoes.entity.Enum.StatusMissao;
import com.infnet.AT.operacoes.service.MissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
// PainelTaticoController já usa /missoes, o Spring permite múltiplos controllers no mesmo path
// desde que os endpoints (@GetMapping) sejam distintos.
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<MissaoResumoDTO>> buscarComFiltro(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo nivelPerigo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFinal,
            @PageableDefault(size = 10) Pageable pageable) {
        
        // Se as datas forem nulas, o repositório pode precisar de tratamento ou valores default
        // No service atual: findByStatusAndNivelPerigoAndDataCriacaoBetween
        return ResponseEntity.ok(missaoService.buscarComFiltro(status, nivelPerigo, dataInicial, dataFinal, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalhadaDTO> detalharMissao(@PathVariable Long id) {
        return ResponseEntity.ok(missaoService.detalharMissao(id));
    }
}
