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
import uff.dac.depraia.apidepraia.dto.EsportistaDTO;
import uff.dac.depraia.apidepraia.model.Esportista;
import uff.dac.depraia.apidepraia.repositories.EsportistaRepository;

@Controller
@RequestMapping("/esportista")
public class EsportistaController {

    @Autowired
    private EsportistaRepository esportistaRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNew(@Valid @RequestBody EsportistaDTO esportista) {
        try {
            esportistaRepo.save(esportista.conversor());
            return "Saved";
        } catch (ConstraintViolationException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Esportista> getAll() {
        return esportistaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Esportista> getById(@PathVariable int id) {
        return esportistaRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Esportista updateById(@RequestBody EsportistaDTO newEsportista, @PathVariable int id) {
        return esportistaRepo.findById(id)
                .map(n -> {
                    n.setTipoUsuario(newEsportista.getTipoUsuario());                  
                    n.getUser().setEmail(newEsportista.getUser().getEmail());
                    n.getUser().setNome(newEsportista.getUser().getNome());
                    n.getUser().getEndereco().setRua(newEsportista.getUser().getEndereco().getRua());
                    n.getUser().getEndereco().setBairro(newEsportista.getUser().getEndereco().getBairro());
                    n.getUser().getEndereco().setCidade(newEsportista.getUser().getEndereco().getCidade());
                    n.getUser().getEndereco().setCep(newEsportista.getUser().getEndereco().getCep());
                    return esportistaRepo.save(n);
                })
                .orElseGet(() -> {
                    return esportistaRepo.save(newEsportista.conversor());
                });
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            esportistaRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
