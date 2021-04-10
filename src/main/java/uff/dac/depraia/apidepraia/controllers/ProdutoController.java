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
import uff.dac.depraia.apidepraia.dto.ProdutoDTO;
import uff.dac.depraia.apidepraia.model.Produto;
import uff.dac.depraia.apidepraia.repositories.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repo;
    
    @PostMapping(path = "")    
    public @ResponseBody
    String addNew(@Valid @RequestBody ProdutoDTO produto) {
        try {
            Produto aux = produto.conversor();
            repo.save(aux);
            return "Saved";
        } catch (ConstraintViolationException e) {            
            return e.getMessage();
        }        
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Produto> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Produto> getById(@PathVariable int id) {
        return repo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Produto updateById(@RequestBody Produto newProduto, @PathVariable int id) {
        try {
            return repo.findById(id)
                .map(n -> {
                    n.setNome(newProduto.getNome());
                    n.setPreco(newProduto.getPreco());                    
                    n.setDescricao(newProduto.getDescricao());
                    return repo.save(n);
                })
                .orElseGet(() -> {
                    return repo.save(newProduto);
                });
        } catch (Exception e) {
            return repo.save(newProduto);
        }
        
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
