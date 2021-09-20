/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.dac.depraia.apidepraia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.jayway.jsonpath.JsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.dto.PraiaDTO;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;

/**
 *
 * @author nicole.l.suet
 */
@SpringBootTest
@AutoConfigureMockMvc

public class PraiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PraiaRepository praiaRepo;

    @Test
    void cadastrarPraiaTest() throws Exception {

        Praia p = new Praia("Praia A", new Endereco("Rua A", "Bairro B", "111111150", "ABC"));
        PraiaDTO praiaDTO = new PraiaDTO(p);

        MvcResult resultCadastro = mockMvc.perform(post("/praia", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(praiaDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String retorno = resultCadastro.getResponse().getContentAsString();
        assertThat(retorno).contains("Saved");

    }

    @Test
    void buscarPraiaPorId() throws Exception {

        int id = -1;
        Iterable<Praia> praias = this.praiaRepo.findAll();
        for (Praia p : praias) {
            id = p.getId();
            break;
        }

        ResultActions result = mockMvc.perform(get("/praia/{id}", id)
                .contentType("application/json"));
        if (id == -1) {
            result.andExpect(status().isInternalServerError());
        } else {
            result.andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                    .andExpect(status().isOk())
                    .andReturn();
        }

    }

    @Test
    void atualizaPraia() throws Exception {

        int id = -1;
        Iterable<Praia> praias = this.praiaRepo.findAll();
        for (Praia p : praias) {
            id = p.getId();
            break;
        }

        Praia p;
        //se achar atualiza
        if (id != -1) {
            p = this.praiaRepo.findById(id).get();
            p.setNome("Novo Nome");
        } //se não achar salva
        else {
            p = new Praia("Praia A", new Endereco("Rua A", "Bairro B", "111111150", "ABC"));
        }

        PraiaDTO praiaDTO = new PraiaDTO(p);

        MvcResult result = mockMvc.perform(put("/praia/{id}", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(praiaDTO)))
                .andExpect(status().isOk())
                .andReturn();

        int idRetornado = JsonPath.read(result.getResponse().getContentAsString(), "id");

        Optional<Praia> atualizada = this.praiaRepo.findById(idRetornado);
        assertThat(atualizada).isNotEmpty();
        assertThat(atualizada.get().getId()).isEqualTo(idRetornado);

    }

    @Test
    void deletaPraia() throws Exception {

        int id = -1;
        Iterable<Praia> praias = this.praiaRepo.findAll();
        for (Praia p : praias) {
            id = p.getId();
            break;
        }

        ResultActions result = mockMvc.perform(delete("/praia/{id}", id).contentType("application/json"));
        if (id == -1) {
            result.andExpect(status().isInternalServerError());
        } else {
            String idRetorno = result
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            assertThat(Integer.parseInt(idRetorno)).isEqualTo(id);
        }

    }
    
    void buscarPraiasTeste() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk());
    }

}
