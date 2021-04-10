package uff.dac.depraia.apidepraia.controllers;

import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.repositories.AgendaRepository;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Ambulante;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepo;
    @Autowired
    private PraiaRepository praiaRepo;
    
    @PostMapping(path = "")
    public @ResponseBody
    Map<String, Boolean> addEntity(@NotNull @Valid @RequestBody AgendaDTO entity) {
        try {
            // Busca a praia pelo ID
            return praiaRepo.findById(entity.getPraia().getId())
                    .map(n -> {       
                        Agenda aux;
                        try {
                            // Preparar                        
                            aux = entity.conversor(n);
                        } catch (Exception ex) {
                            return Mensagem.error(ex.getMessage(), 5);
                        }

                        // Salvar                   
                        agendaRepo.save(aux);
                        return Mensagem.sucesso(aux.getClass().getSimpleName(), 1);
                    })
                    .orElseGet(() -> {
                        return Mensagem.error("Praia", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        }
    }
    
    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Agenda> getAll() {
        return agendaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Agenda> getById(@PathVariable int id) {
        return agendaRepo.findById(id);
    }
}
