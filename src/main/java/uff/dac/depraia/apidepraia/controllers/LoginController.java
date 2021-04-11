package uff.dac.depraia.apidepraia.controllers;

import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.dto.LoginDTO;
import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginRepository loginRepo;

    @PostMapping(path = "")
    @ResponseBody
    ResponseEntity<User> addEntity(@NotNull @Valid @RequestBody LoginDTO entity) {
        // Busca agenda pelo ID
        return loginRepo.login(entity.getEmail(), entity.getSenha())
                .map(n -> {                    
                    return new ResponseEntity<>(n, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);                    
                });
    }
}
