package com.matheus.transacao_api.Controller;

import com.matheus.transacao_api.Controller.dtos.EstatisticasResponseDTO;
import com.matheus.transacao_api.business.services.EstatisticasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor

public class EstatisticasController {
    private final EstatisticasService estatisticasService;

    @GetMapping
    @Operation(description = "Endpoint responsavel por buscar estatisticas de transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na busca das estatisticas"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(@RequestParam(value = "IntervaloBusca",
            required = false, defaultValue = "60")Integer intervaloBusca){
        return ResponseEntity.ok((estatisticasService.calcularEstatisticasTransacoes(intervaloBusca)));
    }
}
