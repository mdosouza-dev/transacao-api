package com.matheus.transacao_api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.matheus.transacao_api.Controller.EstatisticasController;
import com.matheus.transacao_api.Controller.TransacaoController;
import com.matheus.transacao_api.Controller.dtos.EstatisticasResponseDTO;
import com.matheus.transacao_api.Controller.dtos.TransacaoRequestDTO;
import com.matheus.transacao_api.Infrastructure.exceptions.UnprocessableEntity;
import com.matheus.transacao_api.business.services.EstatisticasService;
import com.matheus.transacao_api.business.services.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    MockMvc mockMvc;

    @Autowired
    final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.of(2026, 3, 27, 20, 47, 0, 0, ZoneOffset.UTC));

    }

    @Test
    void deveAdicionarTransacaoComSucesso() throws Exception {

        doNothing().when(transacaoService).adicionarTransacoes(transacao);

        mockMvc.perform(get("/transacao")
                        .content(new ObjectMapper().writeValueAsString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }

    @Test
    void deveGerarExcecaoAoAdicionarTransacao() throws Exception {
        doThrow(new UnprocessableEntity("Erro de requisição")).when(transacaoService).adicionarTransacoes(transacao);

        mockMvc.perform(get("/transacao")
                        .content(new ObjectMapper().writeValueAsString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError());
    }

    @Test
    void deveDeletarTransacoesComSucesso() throws Exception {
        doNothing().when(transacaoService).limparTransacoes();

        mockMvc.perform(get("/delete"))
                .andExpect(status().isOk());
    }

}
