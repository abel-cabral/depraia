package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import javax.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;   
    private final Gson gson = new Gson();    

    @Test
    @Order(2)
    void autenticarTest() throws Exception {
        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        User user = new User("Abel Cabral Strondemberg", "731.886.080-12", endereco, "email@gmail.com", 2, "killer_queen");
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(gson.toJson(user))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andReturn();
    }

    @Test
    @Order(3)
    void pegarUsuariosTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void agendasUsuarioTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Integer id = JsonPath.read(content, "$[0].id");

        mockMvc.perform(MockMvcRequestBuilders.get("/agendasUsuario/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void criarUsuarioTest() throws Exception {
        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        User user = new User("Abel Cabral Strondemberg", "731.886.080-12", endereco, "email@gmail.com", 2, "killer_queen");
        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro")
                .content(gson.toJson(user))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
