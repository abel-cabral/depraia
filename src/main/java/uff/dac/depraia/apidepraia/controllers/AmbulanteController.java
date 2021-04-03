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
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.Produto;
import uff.dac.depraia.apidepraia.repositories.AmbulanteRepository;

@Controller
@RequestMapping("/ambulante")
public class AmbulanteController {

    @Autowired
    private AmbulanteRepository repo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNew(@Valid @RequestBody Ambulante ambulante) {
        try {
            repo.save(ambulante);
            return "Saved";
        } catch (ConstraintViolationException e) {
            return e.getMessage();
        }
    }

    @PostMapping(path = "/adicionar/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Optional<Ambulante> addProduto(@Valid @RequestBody Produto newProduto, @PathVariable int id) {
        //  Optional<Ambulante> n = repo.findById(id);
        //  n.get().getProdutos().add(newProduto);
        // repo.save(n);
        // return "Saved";
        
        return repo.findById(id)
                .map(n -> {
                    n.getProdutos().add(newProduto);
                    return repo.save(n);
                });
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Ambulante> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Ambulante> getById(@PathVariable int id) {
        return repo.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
