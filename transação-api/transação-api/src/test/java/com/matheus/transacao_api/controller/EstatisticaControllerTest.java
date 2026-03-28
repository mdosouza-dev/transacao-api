package com.matheus.transacao_api.controller;


import com.matheus.transacao_api.Controller.EstatisticasController;
import com.matheus.transacao_api.Controller.dtos.EstatisticasResponseDTO;
import com.matheus.transacao_api.business.services.EstatisticasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticaControllerTest {

    @InjectMocks
    EstatisticasController estatisticasController;

    @Mock
    EstatisticasService estatisticasService;

    EstatisticasResponseDTO estatisticas;

    MockMvc mockMvc;

    @BeforeEach
    void setUo(){
        mockMvc = MockMvcBuilders.standaloneSetup(estatisticasController).build();
        estatisticas = new EstatisticasResponseDTO(1l, 20.0,20.0,20.0,20.0);

    }

    @Test
    void deveBuscarEstatisticasComSucesso() throws Exception {

        when(estatisticasService.calcularEstatisticasTransacoes(60)).thenReturn(estatisticas);

        mockMvc.perform(get("/estatistica")
                        .param("intervaloBusca", "60")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.count").value(estatisticas.count()));

    }

}
