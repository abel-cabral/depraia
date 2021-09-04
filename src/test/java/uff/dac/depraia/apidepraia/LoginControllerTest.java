package uff.dac.depraia.apidepraia;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import uff.dac.depraia.apidepraia.dto.UserDTO;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginRepository loginRepo;

    private final Endereco endereco = new Endereco("Rua X", "Bairro Y", "24210201", "Niterói");
    private final User user = new User("Abel Cabral Strondemberg", "731.886.080-12", endereco, "email@gmail.com", 2, "killer_queen");
    private final Gson gson = new Gson();
    private final String json = gson.toJson(user);
    
    @Test
    void autenticarTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andReturn();
        // Como pegar valores do mockMvc!
        // String content = result.getResponse().getContentAsString();
        // this.id = JsonPath.read(content, "$.id");
    }

    @Test
    void pegarUsuariosTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void agendasUsuarioTest() throws Exception {
        Integer id = buscarDados().getBody().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/agendasUsuario/{id}", id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println(id);
    }

    @Test
    void criarUsuarioTest() throws Exception {
        // Busca o id do usuário e deleta;
        Integer id = buscarDados().getBody().getId();
        loginRepo.deleteById(id);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    private ResponseEntity<UserDTO> buscarDados() {        
        return loginRepo.login(user.getEmail(), user.getSenha())
                .map(n -> {
                    return new ResponseEntity<>(new UserDTO(n), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }    
}
