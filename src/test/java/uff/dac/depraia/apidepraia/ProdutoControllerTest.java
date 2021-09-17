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
import uff.dac.depraia.apidepraia.model.Produto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private final Gson gson = new Gson();

    @Test
    @Order(1)
    void cadastrarProdutoTest() throws Exception {
        Produto entity = new Produto("Biscoito Globo", "Original do Rio", 2.00);
        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                .content(gson.toJson(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void buscarProdutosTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void buscarProdutoTest() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    /*
    @Test
    @Order(3)
    void atualizarProdutoTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/produto/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Produto entity = gson.fromJson(content, Produto.class);
        entity.setNome("Guaravita");
        entity.setPreco(1.50);

        mockMvc.perform(MockMvcRequestBuilders.put("/produto/{id}", 1)
                .content(gson.toJson(entity))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    */
    
    @Test
    @Order(4)
    void deletarProdutoTest() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders.delete("/produto/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
