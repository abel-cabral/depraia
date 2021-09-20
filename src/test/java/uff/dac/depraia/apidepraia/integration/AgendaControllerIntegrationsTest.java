/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.dac.depraia.apidepraia.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Iterables;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.controllers.AgendaController;
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.dto.SimplesUsuarioDTO;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.repositories.AgendaRepository;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;

/**
 *
 * @author nicole.l.suet
 */
@SpringBootTest
@AutoConfigureMockMvc

public class AgendaControllerIntegrationsTest {

    @Autowired
    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgendaController agendaController;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PraiaRepository praiaRepo;

    @Test
    @Order(1)
    void cadastrarAgendaTeste() throws Exception {
        
        Iterable<Praia> praiaIterator = praiaRepo.findAll();
        Praia p = praiaIterator.iterator().next();
        Agenda entity = new Agenda(p, new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2021"), 10);
        AgendaDTO agendaDTO = new AgendaDTO(entity);

        if (Iterables.isEmpty(praiaIterator)) {
            String retorno = this.cadastrarPraia(agendaDTO);
            assertThat(retorno).contains("Error! Praia nÃ£o foi encontrado");
        } else {
            String retorno = this.cadastrarPraia(agendaDTO);
            assertThat(retorno).contains("Sucesso! Agenda foi adicionado ao banco de dados");
        }
    }

    @Test
    @Order(2)
    void buscarAgendaPorId() throws Exception {
        int idAgenda = -1;
        Iterable<Agenda> agendas = this.agendaRepository.findAll();
        for (Agenda a : agendas) {
            idAgenda = a.getId();
            break;
        }
        Optional<Agenda> agenda = agendaRepository.findById(idAgenda);
        AgendaDTO retornoAPI = this.buscarAgendaPorId(idAgenda);
        if (retornoAPI == null) {
            assertThat(agenda.isEmpty()).isTrue();
        } else {
            assertThat(agenda.isEmpty()).isFalse();
        }
    }

    @Test

    @Order(4)
    void atualizarAgendaQueExisteTeste() throws Exception {

        Iterable<Praia> praiaIterator = praiaRepo.findAll();
        Praia p = praiaIterator.iterator().next();
        String content = buscarAgendas().getResponse().getContentAsString();
        int id = JsonPath.read(content, "$[0].id");
        Agenda entity = new Agenda(p, new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2021"), 10);
        AgendaDTO agendaDTO = new AgendaDTO(entity);

        if (Iterables.isEmpty(praiaIterator)) {
            String retorno = this.atulizarAgenda(agendaDTO, id);
            assertThat(retorno).contains("Error! Praia nÃ£o foi encontrado");
        } else {
            String retorno = this.atulizarAgenda(agendaDTO, id);
            assertThat(retorno).contains("Sucesso! Agenda foi atualizado no banco de dados");
        }
    }

    @Test
    @Order(5)
    void atualizarAgendaQueNaoExisteTeste() throws Exception {

        Iterable<Praia> praiaIterator = praiaRepo.findAll();
        Praia p = praiaIterator.iterator().next();
        int id = -1;
        Agenda entity = new Agenda(p, new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2021"), 10);
        AgendaDTO agendaDTO = new AgendaDTO(entity);

        if (Iterables.isEmpty(praiaIterator)) {
            String retorno = this.atulizarAgenda(agendaDTO, id);
            assertThat(retorno).contains("Error! Praia nÃ£o foi encontrado");
        } else {
            String retorno = this.atulizarAgenda(agendaDTO, id);
            assertThat(retorno).contains("Error! Agenda nÃ£o foi encontrado");
        }
    }

    @Test
    @Order(7)
    void deletarAgendaQueNaoExiste() throws Exception {

        String resposta = mockMvc.perform(MockMvcRequestBuilders.delete("/agenda/{id}", -1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(resposta).contains("Error! Agenda nÃ£o foi encontrado");

    }

    @Test
    @Order(8)
    void deletarAgendaQueExiste() throws Exception {
        String content = buscarAgendas().getResponse().getContentAsString();
        int id = JsonPath.read(content, "$[0].id");
        String resposta = mockMvc.perform(MockMvcRequestBuilders.delete("/agenda/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Optional<Agenda> agenda = agendaRepository.findById(id);
        assertThat(agenda).isEmpty();
        assertThat(resposta).contains("Sucesso! Agenda foi deletado do banco de dados");

    }

    @Test
    @Order(6)
    void reservarTeste() throws Exception {
        int idAgenda = -1;
        Iterable<Agenda> agendas = this.agendaRepository.findAll();
        for (Agenda a : agendas) {
            idAgenda = a.getId();
            break;
        }
        int idUsuario = -1;
        Iterable<User> usuarios = this.loginRepository.findAll();
        for (User u : usuarios) {
            idUsuario = u.getId();
            break;
        }
        SimplesUsuarioDTO entity = new SimplesUsuarioDTO();
        entity.setAgendaId(idAgenda);
        entity.setUserId(idUsuario);

        String resposta = mockMvc.perform(MockMvcRequestBuilders.put("/agenda/reservar")
                .content(objectMapper.writeValueAsString(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        if (idUsuario == -1) {
            assertThat(resposta).contains("UsuÃ¡rio nÃ£o foi encontrado");
        } else if (idAgenda == -1) {
            assertThat(resposta).contains("Agenda nÃ£o foi encontrado");
        } else {
            assertThat(resposta).contains("A Reserva do usuÃ¡rio foi realizada");

        }

    }

    @Test
    @Order(8)
    void cancelarReservaTeste() throws Exception {

        int idAgenda = -1;
        Iterable<Agenda> agendas = this.agendaRepository.findAll();
        for (Agenda a : agendas) {
            idAgenda = a.getId();
            break;
        }
        int idUsuario = -1;

        Iterable<User> usuarios = this.loginRepository.findAll();
        for (User u : usuarios) {
            idUsuario = u.getId();
            break;
        }

        SimplesUsuarioDTO entity = new SimplesUsuarioDTO();
        entity.setAgendaId(idAgenda);
        entity.setUserId(idUsuario);

        String resposta = mockMvc.perform(MockMvcRequestBuilders.delete("/agenda/cancelar")
                .content(objectMapper.writeValueAsString(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        if (idUsuario == -1) {
            assertThat(resposta).contains("UsuÃ¡rio nÃ£o foi encontrado");
        } else if (idAgenda == -1) {
            assertThat(resposta).contains("Agenda nÃ£o foi encontrado");
        } else {
            assertThat(resposta).contains("A Reserva do usuÃ¡rio foi cancelada foi  banco de dados");
        }

    }

    //FUNCTIONS
    String cadastrarPraia(AgendaDTO agendaDTO) throws Exception {
        MvcResult resultCadastro = mockMvc.perform(post("/agenda", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(agendaDTO)))
                .andExpect(status().isOk())
                .andReturn();

        return resultCadastro.getResponse().getContentAsString();
    }

    String atulizarAgenda(AgendaDTO agendaDTO, int id) throws Exception {
        MvcResult resultCadastro = mockMvc.perform(put("/agenda/{id}", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(agendaDTO)))
                .andExpect(status().isOk())
                .andReturn();

        return resultCadastro.getResponse().getContentAsString();
    }

    AgendaDTO buscarAgendaPorId(Integer id) throws Exception {

        MvcResult result = mockMvc.perform(get("/agenda/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String retorno = result.getResponse().getContentAsString();
        if ("null".equals(retorno)) {
            return null;
        }

        return objectMapper.readValue(retorno, AgendaDTO.class);
    }

    MvcResult buscarAgendas() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/agenda/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk())
                .andReturn();
    }
}
