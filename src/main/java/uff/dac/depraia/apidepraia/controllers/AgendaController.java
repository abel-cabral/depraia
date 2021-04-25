package uff.dac.depraia.apidepraia.controllers;

import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.util.Map;
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
import uff.dac.depraia.apidepraia.dto.AgendaDTO;
import uff.dac.depraia.apidepraia.dto.SimplesUsuarioDTO;
import uff.dac.depraia.apidepraia.repositories.AgendaRepository;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.util.Mensagem;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.repositories.LoginRepository;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepo;
    @Autowired
    private PraiaRepository praiaRepo;
    @Autowired
    private LoginRepository loginRepo;
    
    @ApiOperation(value = "Cria uma nova agenda para uma praia")
    @PostMapping(path = "")
    public @ResponseBody
    Map<String, Boolean> cadastrarAgenda(@NotNull @Valid @RequestBody AgendaDTO entity) {
        try {
            // Busca a praia pelo ID
            return praiaRepo.findById(entity.getPraia().getId())
                    .map(n -> {
                        Agenda aux;
                        try {
                            // Preparar                        
                            aux = entity.conversor(n);
                        } catch (ParseException ex) {
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
    
    @ApiOperation(value = "Lista todas as agendas cadastradas")
    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Agenda> verAgendas() {
        return agendaRepo.findAll();
    }

    @ApiOperation(value = "Busca uma agenda por ID")
    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Agenda> verAgenda(@PathVariable int id) {
        return agendaRepo.findById(id);
    }
    
    @ApiOperation(value = "Atualia os dados de uma agenda")
    @PutMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> AtualizarAgenda(@NotNull @Valid @RequestBody AgendaDTO entity, @PathVariable int id) {
        try {
            // Busca praia pelo ID                                
            return praiaRepo.findById(entity.getPraia().getId()).map(n -> {
                // Busca no banco de dados
                return agendaRepo.findById(id)
                        .map(m -> {
                            // Preparar
                            Agenda aux;
                            try {
                                // Preparar                        
                                aux = entity.conversor(n);
                            } catch (Exception ex) {
                                return Mensagem.error(ex.getMessage(), 5);
                            }

                            m.setData(aux.getData());
                            m.setVagas(aux.getVagas());
                            m.setPraia(n);

                            // Salva                                                         
                            agendaRepo.save(m);
                            return Mensagem.sucesso(m.getClass().getSimpleName(), 2);
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Agenda", 4);
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
    
    @ApiOperation(value = "Deleta uma agenda por ID")
    @DeleteMapping("/{id}")
    public @ResponseBody
    Map<String, Boolean> DeletarAgenda(@NotNull @Valid @RequestBody @PathVariable int id) {
        try {
            // Busca no banco de dados
            return agendaRepo.findById(id)
                    .map(m -> {
                        // Salvar                          
                        agendaRepo.delete(m);
                        return Mensagem.sucesso(m.getClass().getSimpleName(), 3);
                    })
                    .orElseGet(() -> {
                        return Mensagem.error("Agenda", 4);
                    });
        } catch (Exception e) {
            return Mensagem.error(e.getMessage(), 5);
        }
    }

    @ApiOperation(value = "Reserva um lugar na praia para um usuarios, banhistas e esportistas")
    @PutMapping(path = "/reservar")
    public @ResponseBody
    Map<String, Boolean> reservarVaga(@RequestBody SimplesUsuarioDTO entity) {
        try {
            //Busca pelo Usuario
            return loginRepo.findById(entity.getUserId()).map(u -> {
                // Busca agenda pelo ID
                return agendaRepo.findById(entity.getAgendaId())
                        .map(a -> {
                            // Verifica se há vagas
                            try {
                                a.adicionarPessoa();
                                a.getUsuarios().add(u);
                            } catch (Exception ex) {
                                return Mensagem.error(ex.getMessage(), 5);
                            }

                            // Salvar                   
                            agendaRepo.save(a);                            
                            return Mensagem.sucesso("A Reserva do usuário foi realizada", 5);
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Agenda", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Usuário", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        }
    }
    
    @ApiOperation(value = "Cancela um lugar na praia para um usuarios, banhistas e esportistas")
    @DeleteMapping(path = "/cancelar")
    public @ResponseBody
    Map<String, Boolean> cancelarVaga(@RequestBody SimplesUsuarioDTO entity) {
        try {
            //Busca pelo Usuario
            return loginRepo.findById(entity.getUserId()).map(u -> {
                // Busca agenda pelo ID
                return agendaRepo.findById(entity.getAgendaId())
                        .map(a -> {
                            // Verifica se há vagas
                            try {
                                a.removerPessoa();
                                a.getUsuarios().remove(u);
                            } catch (Exception ex) {
                                return Mensagem.error(ex.getMessage(), 5);
                            }

                            // Salvar                   
                            agendaRepo.save(a);                            
                            return Mensagem.sucesso("A Reserva do usuário foi cancelada", 5);
                        })
                        .orElseGet(() -> {
                            return Mensagem.error("Agenda", 4);
                        });
            })
                    .orElseGet(() -> {
                        return Mensagem.error("Usuário", 4);
                    });
        } catch (NullPointerException e) {
            return Mensagem.error("Formato JSON inválido, verifique e tente novamente", 5);
        }
    }
}
