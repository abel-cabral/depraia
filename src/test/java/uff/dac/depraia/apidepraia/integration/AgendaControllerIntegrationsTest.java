/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.dac.depraia.apidepraia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.controllers.AgendaController;
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;

/**
 *
 * @author nicole.l.suet
 */
@SpringBootTest
@AutoConfigureMockMvc

public class AgendaControllerIntegrationsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgendaController agendaController;

    @Autowired
    private PraiaRepository praiaRepo;

    @Test
    void registrationWorksThroughAllLayers() throws Exception {

        Iterable<Praia> praias = praiaRepo.findAll();
        //first praia element found in DB
        Praia praia = Iterables.get(praias, 0);
        Agenda agenda = new Agenda(praia, new Date(), 10);

        MvcResult body = mockMvc.perform(post("/agenda/", 42L)
                .contentType("application/json")
                .content("{'data': 'string', 'praia': { 'id': 0 }, 'vagas': 0}"))
                .andExpect(status().isOk())
                .andReturn();
        
        System.out.println(body);
        //assertThat:=(userEntity.getEmail()).isEqualTo("zaphod@galaxy.net");;;;
    }

}
