package uff.dac.depraia.apidepraia.controllers;

import java.util.Optional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;

@Controller
@RequestMapping("/praia")
public class PraiaController {
    
    @Autowired
    private PraiaRepository praiaRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})    
    public @ResponseBody
    String addNew(@Valid @RequestBody Praia praia) {
        try {
            praiaRepo.save(praia);
            return "Saved";
        } catch (ConstraintViolationException e) {            
            return e.getMessage();
        }        
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Praia> getAll() {
        return praiaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Praia> getById(@PathVariable int id) {
        return praiaRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Praia updateById(@RequestBody Praia newPraia, @PathVariable int id) {
        try {
            return praiaRepo.findById(id)
                .map(n -> {
                    n.setNome(newPraia.getNome());
                    n.setCapacidade(newPraia.getCapacidade());
                    n.getEndereco().setBairro(newPraia.getEndereco().getBairro());
                    n.getEndereco().setRua(newPraia.getEndereco().getRua());
                    n.getEndereco().setCidade(newPraia.getEndereco().getCidade());
                    n.getEndereco().setCep(newPraia.getEndereco().getCep());
                    return praiaRepo.save(n);
                })
                .orElseGet(() -> {
                    return praiaRepo.save(newPraia);
                });
        } catch (Exception e) {
            return praiaRepo.save(newPraia);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            praiaRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
