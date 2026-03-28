package com.matheus.transacao_api.business.services;

import com.matheus.transacao_api.Controller.dtos.TransacaoRequestDTO;
import com.matheus.transacao_api.Infrastructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionarTransacoes(TransacaoRequestDTO dto){

        log.info("Iniciado o processamento de gravar transações" + dto);

        if (dto.dataHora().isAfter(OffsetDateTime.now())){
            log.error("data e hora maiores que a atual");
            throw new UnprocessableEntity("Data e Hora maiores que data e hora atuais");
        }
        if (dto.valor() < 0){
            log.error("Valor não pode ser menor que 0");
            throw new UnprocessableEntity("Valor não pode ser menor que 0");
        }

        listaTransacoes.add(dto);
        log.info("Transações adicionas com sucesso");
    }

    public void limparTransacoes(){
        log.info("Iniciado processamento para deletar trnasações");
        listaTransacoes.clear();
        log.info("Transações deletadas com sucesso");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBuscas){
        log.info("Iniciadas buscas de transações por tempo " + intervaloBuscas);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBuscas);

        log.info("Retorno de transações com sucesso");
        return listaTransacoes.stream()
                    .filter( transacao -> transacao.dataHora()
                        .isAfter(dataHoraIntervalo)).toList();

    }
}
