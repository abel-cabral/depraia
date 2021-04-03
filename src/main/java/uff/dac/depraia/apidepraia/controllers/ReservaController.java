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
import uff.dac.depraia.apidepraia.model.Reserva;
import uff.dac.depraia.apidepraia.repositories.ReservaRepository;

@Controller
@RequestMapping("/reserva")
public class ReservaController {
    
    @Autowired
    private ReservaRepository reservaRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})    
    public @ResponseBody
    String addNew(@Valid @RequestBody Reserva reserva) {
        try {
            reservaRepo.save(reserva);
            return "Saved";
        } catch (ConstraintViolationException e) {            
            return e.getMessage();
        }        
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Reserva> getAll() {
        return reservaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Reserva> getById(@PathVariable int id) {
        return reservaRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Reserva updateById(@RequestBody Reserva newReserva, @PathVariable int id) {
        try {
            return reservaRepo.findById(id)
                .map(n -> {
                    n.getUser().setCpf(newReserva.getUser().getCpf());
                    n.getUser().setEmail(newReserva.getUser().getEmail());
                    n.getUser().setNome(newReserva.getUser().getNome());
                    n.getUser().getEndereco().setRua(newReserva.getUser().getEndereco().getRua());
                    n.getUser().getEndereco().setBairro(newReserva.getUser().getEndereco().getBairro());
                    n.getUser().getEndereco().setCidade(newReserva.getUser().getEndereco().getCidade());
                    n.getUser().getEndereco().setCep(newReserva.getUser().getEndereco().getCep());
                    n.getPraia().setCapacidade(newReserva.getPraia().getCapacidade());
                    n.getPraia().setNome(newReserva.getPraia().getNome());
                    n.getPraia().getEndereco().setRua(newReserva.getPraia().getEndereco().getRua());
                    n.getPraia().getEndereco().setBairro(newReserva.getPraia().getEndereco().getBairro());
                    n.getPraia().getEndereco().setCidade(newReserva.getPraia().getEndereco().getCidade());
                    n.getPraia().getEndereco().setCep(newReserva.getPraia().getEndereco().getCep());
                    return reservaRepo.save(n);
                })
                .orElseGet(() -> {
                    return reservaRepo.save(newReserva);
                });
        } catch (Exception e) {
            return reservaRepo.save(newReserva);
        }
        
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
        try {
            reservaRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
