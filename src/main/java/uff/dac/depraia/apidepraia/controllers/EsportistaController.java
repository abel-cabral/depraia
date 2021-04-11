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
import uff.dac.depraia.apidepraia.dto.CadastroDTO;
import uff.dac.depraia.apidepraia.dto.EsportistaDTO;
import uff.dac.depraia.apidepraia.model.Esportista;
import uff.dac.depraia.apidepraia.repositories.AgendaRepository;
import uff.dac.depraia.apidepraia.repositories.EsportistaRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;

@Controller
@RequestMapping("/esportista")
public class EsportistaController {

    @Autowired
    private EsportistaRepository esportistaRepo;
    @Autowired
    private AgendaRepository agendaRepo;

    @PostMapping(path = "")
    public @ResponseBody
    Map<String, Boolean> addEntity(@NotNull @Valid @RequestBody CadastroDTO entity) {        
        try {
            // Busca agenda pelo ID
            return agendaRepo.findById(entity.getAgenda().getId())
                    .map(n -> {
                        // Preparar                        
                        Esportista aux = entity.conversorEsportista(n);

                        // Verifica se há vagas
                        try {
                            n.adicionarPessoa();
                       } catch (Exception ex) {
                            return Mensagem.error(ex.getMessage(), 5);
                       }

                        // Salvar                   
                        esportistaRepo.save(aux);
                        return Mensagem.sucesso(aux.getClass().getSimpleName(), 1);
                    })
                    .orElseGet(() -> {
                        return Mensagem.error("Agenda", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
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
    Map<String, Boolean> updateById(@NotNull @Valid @RequestBody EsportistaDTO entity, @PathVariable int id) {
        try {
            // Busca agenda pelo ID                                
            return agendaRepo.findById(entity.getAgenda().getId()).map(n -> {
                // Busca no banco de dados
                return esportistaRepo.findById(id)
                        .map(m -> {
                            // Preparar 
                            m.getUser().setTipoUsuario(entity.getUser().getTipoUsuario());
                            m.getUser().setNome(entity.getUser().getNome());
                            m.getUser().setCpf(entity.getUser().getCpf());
                            m.getUser().setEmail(entity.getUser().getEmail());
                            m.getUser().getEndereco().setRua(entity.getUser().getEndereco().getRua());
                            m.getUser().getEndereco().setBairro(entity.getUser().getEndereco().getBairro());
                            m.getUser().getEndereco().setCep(entity.getUser().getEndereco().getCep());
                            m.getUser().getEndereco().setCidade(entity.getUser().getEndereco().getCidade());

                            // Verifica se há vaga na agenda e entao faz as operacoes de acrescentar e decrementar                           
                            try {
                                // Verifica se a nova agenda tem lugar
                                n.adicionarPessoa();
                                // Libera vaga na agenda antiga e a salva
                                m.getAgenda().removerPessoa();
                                agendaRepo.save(m.getAgenda());
                                // Salva a vaga nova agenda
                                m.setAgenda(n);                                
                                esportistaRepo.save(m);
                                return Mensagem.sucesso(m.getClass().getSimpleName(), 2);                                
                            } catch (Exception ex) {                                
                                return Mensagem.error(ex.getMessage(), 5);
                            }
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Esportista", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Agenda", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        } catch (Exception e) {
            return Mensagem.error(e.getMessage(), 5);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> deleteById(@NotNull @Valid @RequestBody EsportistaDTO entity, @PathVariable int id) {
        try {
            // Busca agenda pelo ID                               
            return agendaRepo.findById(entity.getAgenda().getId()).map(n -> {
                // Busca no banco de dados
                return esportistaRepo.findById(id)
                        .map(m -> {
                            // Verifica se o usuario está incluido na agenda informada
                            if(!Objects.equals(n.getId(), m.getAgenda().getId())) {
                                return Mensagem.error(entity.getClass().getSimpleName() + " não está cadastrado na agenda informada", 5);
                            }
                            // Libera Vaga
                            m.setAgenda(n);
                            try {                                                                
                                m.getAgenda().removerPessoa();                                
                            } catch (Exception ex) {                                
                                return Mensagem.error(ex.getMessage(), 5);
                            }

                            // Salvar                        
                            esportistaRepo.delete(m);
                            return Mensagem.sucesso(m.getClass().getSimpleName(), 3);
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Esportista", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Agenda", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        } catch (Exception e) {
            return Mensagem.error(e.getMessage(), 5);
        }
    }
}
