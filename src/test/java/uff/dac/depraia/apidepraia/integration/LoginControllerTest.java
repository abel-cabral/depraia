/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.dac.depraia.apidepraia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.dto.UserDTOSenha;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.AgendaRepository;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;

/**
 *
 * @author nicole.l.suet
 */
@SpringBootTest
@AutoConfigureMockMvc

public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AgendaRepository agendaRepo;

    @Test
    void agendasUsuarioTest() throws Exception {

        int id = -1;
        Iterable<User> usuarios = loginRepository.findAll();
        for (User u : usuarios) {
            id = u.getId();
            break;
        }

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/agendasUsuario/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        if (id == -1) {
            result.andExpect(status().isInternalServerError());
        } else {
            result.andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        }

    }

    @Test
    void cadastroUsuario() throws Exception {

        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        User usuario = new User("ABC", "12345678910", endereco, "teste@teste.com", 0, "123456789");
        UserDTOSenha body = new UserDTOSenha(usuario);
        
        MvcResult resultCadastro = mockMvc.perform(post("/cadastro")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn();
        
        assertThat(resultCadastro.getResponse().getContentAsString()).contains("Sucesso!");

    }

}
