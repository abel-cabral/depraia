package uff.dac.depraia.apidepraia.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.model.Endereco;
import uff.dac.depraia.apidepraia.repositories.EnderecoRepository;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepo;

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String addNewAdress(@RequestBody Endereco endereco) {
        Endereco n = new Endereco();
        enderecoRepo.save(endereco);
        return "Saved";
    }

    @GetMapping(path = "/todos")
    public @ResponseBody Iterable<Endereco> getAllAdresses() {
        return enderecoRepo.findAll();
    }
    
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Endereco> getById(@PathVariable int id) {  
        return enderecoRepo.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deletePost(@PathVariable Integer id) {
        try {
            enderecoRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
