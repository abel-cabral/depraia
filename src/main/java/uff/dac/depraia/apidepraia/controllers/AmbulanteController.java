package uff.dac.depraia.apidepraia.controllers;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import uff.dac.depraia.apidepraia.dto.AmbulanteDTO;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.repositories.AmbulanteRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;

@Controller
@RequestMapping("/ambulante")
public class AmbulanteController {

    @Autowired
    private AmbulanteRepository ambulanteRepo;
    @Autowired
    private PraiaRepository praiaRepo;

    @PostMapping(path = "")
    public @ResponseBody
    Map<String, Boolean> addEntity(@NotNull @Valid @RequestBody AmbulanteDTO entity) {        
        try {
            // Busca praia pelo ID
            return praiaRepo.findById(entity.getPraia().getId())
                    .map(n -> {
                        // Preparar                        
                        Ambulante aux = entity.conversor(n);
                        
                        // Salvar                   
                        ambulanteRepo.save(aux);
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
    Iterable<Ambulante> getAll() {
        return ambulanteRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Ambulante> getById(@PathVariable int id) {
        return ambulanteRepo.findById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> updateById(@NotNull @Valid @RequestBody AmbulanteDTO entity, @PathVariable int id) {
        try {
            // Busca praia pelo ID                                
            return praiaRepo.findById(entity.getPraia().getId()).map(n -> {
                // Busca no banco de dados
                return ambulanteRepo.findById(id)
                        .map(m -> {
                            // Preparar                             
                            m.getUser().setNome(entity.getUser().getNome());
                            m.getUser().setCpf(entity.getUser().getCpf());
                            m.getUser().setEmail(entity.getUser().getEmail());
                            m.getUser().setAdmin(entity.getUser().getAdmin());
                            m.getUser().getEndereco().setRua(entity.getUser().getEndereco().getRua());
                            m.getUser().getEndereco().setBairro(entity.getUser().getEndereco().getBairro());
                            m.getUser().getEndereco().setCep(entity.getUser().getEndereco().getCep());
                            m.getUser().getEndereco().setCidade(entity.getUser().getEndereco().getCidade());

                            // Salva
                            m.setPraia(n);                                
                            ambulanteRepo.save(m);
                            return Mensagem.sucesso(m.getClass().getSimpleName(), 2);                 
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Ambulante", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Praia", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        } catch (Exception e) {
            return Mensagem.error(e.getMessage(), 5);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> deleteById(@NotNull @Valid @RequestBody AmbulanteDTO entity, @PathVariable int id) {
        try {
            // Busca praia pelo ID                               
            return praiaRepo.findById(entity.getPraia().getId()).map(n -> {
                // Busca no banco de dados
                return ambulanteRepo.findById(id)
                        .map(m -> {
                            // Verifica se o usuario está incluido na praia informada
                            if(!Objects.equals(n.getId(), m.getPraia().getId())) {
                                return Mensagem.error(entity.getClass().getSimpleName() + " não está cadastrado na praia informada", 5);
                            }                                                        
                            // Salvar                        
                            m.setPraia(n);                           
                            ambulanteRepo.delete(m);
                            return Mensagem.sucesso(m.getClass().getSimpleName(), 3);
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Ambulante", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Praia", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        } catch (Exception e) {
            return Mensagem.error(e.getMessage(), 5);
        }
    }

}
