package uff.dac.depraia.apidepraia.controllers;

import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.dto.QuiosqueDTO;
import uff.dac.depraia.apidepraia.model.Quiosque;
import uff.dac.depraia.apidepraia.repositories.QuiosqueRepository;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;

@Controller
@RequestMapping("/quiosque")
public class QuiosqueController {

    @Autowired
    private QuiosqueRepository quiosqueRepo;
    @Autowired
    private PraiaRepository praiaRepo;

    @PostMapping(path = "")
    public @ResponseBody
    Map<String, Boolean> addEntity(@Valid @RequestBody QuiosqueDTO entity) {
        // Busca a praia pelo ID
        return praiaRepo.findById(entity.getPraia().getId())
                .map(n -> {
                    Quiosque aux = entity.conversor();
                    aux.setPraia(n);
                    quiosqueRepo.save(aux);
                    return Mensagem.sucesso(entity.conversor().getClass().getSimpleName(), 1);
                })
                .orElseGet(() -> {
                    return Mensagem.error("Praia", 4);
                });
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
    Map<String, Boolean> updateById(@Valid @RequestBody QuiosqueDTO entity, @PathVariable int id) {
        // Busca se o id da praia é valido                                
        return praiaRepo.findById(entity.getPraia().getId()).map(n -> {
            // Busca o quiosque no banco de dados
            return quiosqueRepo.findById(id)
                    .map(m -> {
                        m.setNome(entity.getNome());
                        m.setPraia(n);
                        quiosqueRepo.save(m);
                        return Mensagem.sucesso(entity.conversor().getClass().getSimpleName(), 2);
                    })
                    .orElseGet(() -> {
                        return Mensagem.error("Quiosque", 4);
                    });
        })
                .orElseGet(() -> {
                    return Mensagem.error("Praia", 4);
                });
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> deleteById(@PathVariable int id) {
        try {
            quiosqueRepo.deleteById(id);
            return Mensagem.sucesso("Quiosque", 3);
        } catch (Exception e) {
            return Mensagem.error("Quiosque", 3);
        }
    }
}
