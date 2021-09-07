package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.Praia;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        // Cria uma praia
        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        Praia entity = new Praia("Boa Viagem", endereco);
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/praia")
                    .content(gson.toJson(entity))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(1)
    void cadastrarProdutoTest() throws Exception {
        // Busca uma Praia pelo id
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Praia praia = gson.fromJson(content, Praia.class);

        // Cria uma Agenda com 1 vaga
        Agenda entity = new Agenda(praia, new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2021"), 1);
        mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
                .content(gson.toJson(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

/*
 // Checar se as vagas foram decrementadas
        mockMvc.perform(MockMvcRequestBuilders.get("/agenda/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vagas").value(0))
                .andExpect(status().isOk());
 */
