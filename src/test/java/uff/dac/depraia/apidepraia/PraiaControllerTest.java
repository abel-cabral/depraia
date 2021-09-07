package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.Praia;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PraiaControllerTest {

    @Autowired
    private MockMvc mockMvc;
        
    private final Gson gson = new Gson();    

    @Test
    @Order(1)
    void criarPraiaTest() throws Exception {
        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        Praia entity = new Praia("Boa Viagem", endereco);
        mockMvc.perform(MockMvcRequestBuilders.post("/praia")
                .content(gson.toJson(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void buscarPraiasTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void buscaPraiaTest() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Boa Viagem"))
                .andExpect(status().isOk());
    }

    @Test    
    @Order(3)
    void atualizarPraiaTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Praia entity = gson.fromJson(content, Praia.class);
        entity.setNome("Icaraí");
        
        mockMvc.perform(MockMvcRequestBuilders.put("/praia/{id}", entity.getId())
                .content(gson.toJson(entity))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Icaraí"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void deletarPraiaTest() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders.delete("/praia/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
