/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.dac.depraia.apidepraia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import uff.dac.depraia.apidepraia.dto.LoginDTO;
import uff.dac.depraia.apidepraia.dto.UserDTOSenha;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {   
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginRepository loginRepo;
    
    Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
    User user = new User("Abel Cabral Strondemberg", "731.886.080-12", endereco, "email@gmail.com", 2, "killer_queen");        

    @Test
    void LoginUserTest() throws Exception {
        LoginDTO entity = new LoginDTO(user);
        Gson gson = new Gson();
        String json = gson.toJson(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int id = JsonPath.read(content, "$.id");
        deleteUserCreated(id);
    }
    
    @Test
    void CreateUserTest() throws Exception {
        UserDTOSenha entity = new UserDTOSenha(user);
        Gson gson = new Gson();
        String json = gson.toJson(entity);
        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());                   
    }

    @Test
    void getUsersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    private void deleteUserCreated(int id) {
        loginRepo.deleteById(id);
    }
}
