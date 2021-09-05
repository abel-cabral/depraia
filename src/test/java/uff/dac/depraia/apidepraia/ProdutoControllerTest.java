package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.model.Produto;
import uff.dac.depraia.apidepraia.repositories.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository entityRepo;

    private final Produto entity = new Produto("Biscoito Globo", "Original do Rio", 2.00);
    private final Gson gson = new Gson();
    private final String json = gson.toJson(entity);

    @Test
    @Order(1)
    void cadastrarProdutoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                .content(json)
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
        Integer id = buscarDado().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    /*
    @Test
    void atualizarProdutoTest() throws Exception {
        Integer id = buscarDado().getId();
        entity.setNome("Guaravita");
        entity.setDescricao("Guaraná Natural");
        entity.setPreco(1.50);
        
        json = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.put("/produto/{id}", id)                
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
     */
    @Test
    @Order(3)
    void deletarProdutoTest() throws Exception {
        Integer id = buscarDado().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/produto/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Produto buscarDado() {
        List<Produto> lista = new ArrayList<>();
        entityRepo.findAll().forEach(lista::add);
        return lista.get(0);
    }
}
