package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PraiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PraiaRepository entityRepo;

    private final Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
    private final Praia entity = new Praia("Boa Viagem", endereco);
    private final Gson gson = new Gson();
    private String json = gson.toJson(entity);

    @Test
    @Order(1)
    void criarPraiaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/praia")
                .content(json)
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
        Integer id = buscarDado().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Boa Viagem"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Order(3)
    void atualizarPraiaTest() throws Exception {
        Praia x = buscarDado();
        x.setNome("Icaraí");
        json = gson.toJson(x);

        mockMvc.perform(MockMvcRequestBuilders.put("/praia/{id}", x.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Icaraí"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void deletarPraiaTest() throws Exception {
        Integer id = buscarDado().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/praia/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Praia buscarDado() {
        List<Praia> lista = new ArrayList<>();
        entityRepo.findAll().forEach(lista::add);
        return lista.get(0);
    }
}
