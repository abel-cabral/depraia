package uff.dac.depraia.apidepraia.controllers;

import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.dto.LoginDTO;
import uff.dac.depraia.apidepraia.dto.UserDTO;
import uff.dac.depraia.apidepraia.dto.UserDTOSenha;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginRepository loginRepo;
    
    @ApiOperation(value = "Recebe os dados do usuario que pediu autenticação")
    @PostMapping(path = "/login")
    @ResponseBody
    ResponseEntity<UserDTO> fazerLogin(@NotNull @Valid @RequestBody LoginDTO entity) {
        // Busca agenda pelo ID
        return loginRepo.login(entity.getEmail(), entity.getSenha())
                .map(n -> {
                    return new ResponseEntity<>(new UserDTO(n), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @ApiOperation(value = "Cadastra um novo usuário no sistemas, banhista ou esportista. Use AMBULANTE_CONTROLLER para cadastrar um ambulante")
    @PostMapping(path = "/cadastro")
    @ResponseBody
    Map<String, Boolean> fazerCadastro(@NotNull @Valid @RequestBody UserDTOSenha entity) {
        // Salvar                   
        loginRepo.save(entity.conversor());
        return Mensagem.sucesso(entity.getClass().getSimpleName(), 1);
    }
    
    @ApiOperation(value = "Todos os usuários cadastrados: Esportista, banhista e ambulante")
    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<UserDTO> getAll() {
        Set<UserDTO> users = new HashSet<>();
        loginRepo.findAll().forEach(u -> {
            users.add(new UserDTO(u));
        });
        return users;
    }
    
    @ApiOperation(value = "Pelo Id, busca todas as agendas que um usuário esta/foi  inscrito")
    @GetMapping(path = "/agendasUsuario/{id}")
    public @ResponseBody
    Iterable<AgendaDTO> agendasUsuario(@PathVariable int id) {
        Set<AgendaDTO> agendas = new HashSet<>();
        loginRepo.findById(id).get().getAgendas().forEach(a -> {
            agendas.add(new AgendaDTO(a));
        });
        return agendas;
    }
}
