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
import uff.dac.depraia.apidepraia.model.Quiosque;
import uff.dac.depraia.apidepraia.repositories.QuiosqueRepository;

@Controller
@RequestMapping("/quiosque")
public class QuiosqueController {
    
    @Autowired
    private QuiosqueRepository quiosqueRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})    
    public @ResponseBody
    String addNew(@Valid @RequestBody Quiosque quiosque) {
        try {
            quiosqueRepo.save(quiosque);
            return "Saved";
        } catch (ConstraintViolationException e) {            
            return e.getMessage();
        }        
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Quiosque> getAll() {
        return quiosqueRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Quiosque> getById(@PathVariable int id) {
        return quiosqueRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Quiosque updateById(@RequestBody Quiosque newQuiosque, @PathVariable int id) {
        try {
            return quiosqueRepo.findById(id)
                .map(n -> {
                    n.setNome(newQuiosque.getNome());
                    n.getPraia().setCapacidade(newQuiosque.getPraia().getCapacidade());
                    n.getPraia().setNome(newQuiosque.getPraia().getNome());
                    n.getPraia().getEndereco().setRua(newQuiosque.getPraia().getEndereco().getRua());
                    n.getPraia().getEndereco().setBairro(newQuiosque.getPraia().getEndereco().getBairro());
                    n.getPraia().getEndereco().setCidade(newQuiosque.getPraia().getEndereco().getCidade());
                    n.getPraia().getEndereco().setCep(newQuiosque.getPraia().getEndereco().getCep());
                    return quiosqueRepo.save(n);
                })
                .orElseGet(() -> {
                    return quiosqueRepo.save(newQuiosque);
                });
        } catch (Exception e) {
            return quiosqueRepo.save(newQuiosque);
        }
        
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            quiosqueRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
