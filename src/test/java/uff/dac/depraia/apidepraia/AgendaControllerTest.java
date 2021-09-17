package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.dto.SimplesUsuarioDTO;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @Test
    @Order(1)
    void cadastrarAgendaTest() throws Exception {
        Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
        Praia praia = new Praia("Boa Viagem", endereco);
        mockMvc.perform(MockMvcRequestBuilders.post("/praia")
                .content(gson.toJson(praia))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // Criar um usuario
        User user = new User("Abel Cabral Strondemberg", "731.886.080-12", endereco, "email@gmail.com", 2, "killer_queen");
        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro")
                .content(gson.toJson(user))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // Busca uma Praia pelo id
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/praia/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Praia p = gson.fromJson(content, Praia.class);

        // Cria uma Agenda com 2 vaga
        Agenda entity = new Agenda(p, new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2021"), 2);
        AgendaDTO agendaDTO = new AgendaDTO(entity);
        mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
                .content(gson.toJson(agendaDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void buscarAgendasTest() throws Exception {
        buscarAgendas();
    }

    @Test
    @Order(2)
    void buscarAgendaTest() throws Exception {
        String content = buscarAgendas().getResponse().getContentAsString();
        int id = JsonPath.read(content, "$[0].id");
        buscarAgenda(id);
    }

    @Test
    @Order(3)
    void fazerReservaTest() throws Exception {
        // Classe que contem 2 atributos: id da praia e da agenda
        String content1 = buscarAgendas().getResponse().getContentAsString();
        int idAgenda = JsonPath.read(content1, "$[0].id");

        String content2 = buscarPraias().getResponse().getContentAsString();
        int idPraia = JsonPath.read(content2, "$[0].id");

        SimplesUsuarioDTO entity = new SimplesUsuarioDTO();
        entity.setAgendaId(idAgenda);
        entity.setUserId(idPraia);

        mockMvc.perform(MockMvcRequestBuilders.put("/agenda/reservar")
                .content(gson.toJson(entity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void alterarAgendaTest() throws Exception {
        String content = buscarAgendas().getResponse().getContentAsString();
        Agenda[] a = gson.fromJson(content, Agenda[].class);
        a[0].setVagas(5);
        // AgendaDTO entity = new AgendaDTO(a[0]);     

        mockMvc.perform(MockMvcRequestBuilders.put("/agenda/{id}", a[0].getId())
                .content(gson.toJson(a[0]))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void cancelarReservaTest() throws Exception {
        String content1 = buscarAgendas().getResponse().getContentAsString();
        int idAgenda = JsonPath.read(content1, "$[0].id");

        String content2 = buscarPraias().getResponse().getContentAsString();
        int idPraia = JsonPath.read(content2, "$[0].id");

        SimplesUsuarioDTO entity = new SimplesUsuarioDTO();
        entity.setAgendaId(idAgenda);
        entity.setUserId(idPraia);

        mockMvc.perform(MockMvcRequestBuilders.delete("/agenda/cancelar")
                .content(gson.toJson(entity))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void deletarAgendaTest() throws Exception {
        String content1 = buscarAgendas().getResponse().getContentAsString();
        int idAgenda = JsonPath.read(content1, "$[0].id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/agenda/{id}", idAgenda)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // FUNCTIONS
    private MvcResult buscarAgendas() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/agenda/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult buscarAgenda(int id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/agenda/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult buscarPraias() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/praia/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult buscarUsuarios() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/login/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
