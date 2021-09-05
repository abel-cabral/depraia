package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
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
public class PraiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PraiaRepository entityRepo;

    private final Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
    private final Praia entity = new Praia("Boa Viagem", endereco);
    private final Gson gson = new Gson();
    private final String json = gson.toJson(entity);

    @Test
    void criarPraiaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/praia")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPraiasTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk());
    }

    @Test
    void buscaPraiaTest() throws Exception {
        Integer id = buscarDado().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Boa Viagem"))
                .andExpect(status().isOk());
    }

    private Praia buscarDado() {
        List<Praia> lista = new ArrayList<>();
        entityRepo.findAll().forEach(lista::add);
        return lista.get(0);
    }
}
