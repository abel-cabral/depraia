package uff.dac.depraia.apidepraia.controllers;

import java.util.Optional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.dto.BanhistaDTO;
import uff.dac.depraia.apidepraia.model.Banhista;
import uff.dac.depraia.apidepraia.repositories.BanhistaRepository;

@Controller
@RequestMapping("/banhista")
public class BanhistaController {

    @Autowired
    private BanhistaRepository banhistaRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNew(@Valid @RequestBody BanhistaDTO banhista) {
        try {
            banhistaRepo.save(banhista.conversor());
            return "Saved";
        } catch (ConstraintViolationException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Banhista> getAll() {
        return banhistaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Banhista> getById(@PathVariable int id) {
        return banhistaRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Banhista updateById(@RequestBody BanhistaDTO newBanhista, @PathVariable int id) {
        return banhistaRepo.findById(id)
                .map(n -> {
                    n.setTipoUsuario(newBanhista.getTipoUsuario());                  
                    n.getUser().setEmail(newBanhista.getUser().getEmail());
                    n.getUser().setNome(newBanhista.getUser().getNome());
                    n.getUser().getEndereco().setRua(newBanhista.getUser().getEndereco().getRua());
                    n.getUser().getEndereco().setBairro(newBanhista.getUser().getEndereco().getBairro());
                    n.getUser().getEndereco().setCidade(newBanhista.getUser().getEndereco().getCidade());
                    n.getUser().getEndereco().setCep(newBanhista.getUser().getEndereco().getCep());
                    return banhistaRepo.save(n);
                })
                .orElseGet(() -> {
                    return banhistaRepo.save(newBanhista.conversor());
                });
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            banhistaRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
