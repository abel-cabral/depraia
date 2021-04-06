package uff.dac.depraia.apidepraia.controllers;

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
import uff.dac.depraia.apidepraia.dto.PraiaDTO;
import uff.dac.depraia.apidepraia.dto.UserDTO;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.repositories.BanhistaRepository;
import uff.dac.depraia.apidepraia.repositories.PraiaRepository;
import uff.dac.depraia.apidepraia.repositories.EsportistaRepository;

@Controller
@RequestMapping("/praia")
public class PraiaController {

    @Autowired
    private PraiaRepository praiaRepo;
    @Autowired
    private BanhistaRepository banhistaRepo;
    @Autowired
    private EsportistaRepository esportistaRepo;

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNew(@Valid @RequestBody PraiaDTO praia) {
        try {
            praiaRepo.save(praia.conversor());
            return "Saved";
        } catch (ConstraintViolationException e) {
            return e.getMessage();
        }
    }

    @PostMapping(path = "adicionar/banhista/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addBanhista(@Valid @RequestBody UserDTO user, @PathVariable Integer id) {
        try {
            // Busca se o Banhista tem cadastro
            if (!banhistaRepo.findByCPF(user.getCpf()).equals("")) {
                // Busca se o id da praia é valido                                
                return praiaRepo.findById(id).map(n -> {
                    // Verifica se o CPF já tá vinculado naquela praia
                    if (!n.buscarCPF(n.getBanhistas(), user.getCpf())) {
                        // Verifica se a praia tem vaga
                        Boolean temVaga = n.addBanhista(user.getCpf());
                        if (temVaga) {
                            praiaRepo.save(n);
                            return "Banhista adicionado a praia " + n.getNome();
                        } else {
                            return "Praia com capacidade máxima atingida";
                        }
                    } else {
                        return "Banhista já está cadastrado";
                    }

                })
                        .orElseGet(() -> {
                            return "Praia não cadastrada";
                        });
            }
        } catch (NullPointerException e) {
            return "Banhista não cadastrado";
        }
        return null;
    }

    @DeleteMapping(path = "remover/banhista/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String delBanhista(@Valid @RequestBody UserDTO user, @PathVariable Integer id) {
        // Busca se o id da praia é valido
        return praiaRepo.findById(id).map(n -> {
            // Verifica se o CPF já tá vinculado naquela praia
            if (n.buscarCPF(n.getBanhistas(), user.getCpf())) {
                n.delBanhista(user.getCpf());
                praiaRepo.save(n);
                return "Banhista removido da praia " + n.getNome();
            } else {
                return "Banhista não está vinculado a praia " + n.getNome();
            }
        })
                .orElseGet(() -> {
                    return "Praia não cadastrada";
                });
    }
    
    // ESPORTISTA
    @PostMapping(path = "adicionar/esportista/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addEsportista(@Valid @RequestBody UserDTO user, @PathVariable Integer id) {
        try {
            // Busca se o Banhista tem cadastro
            if (!esportistaRepo.findByCPF(user.getCpf()).equals("")) {
                // Busca se o id da praia é valido                                
                return praiaRepo.findById(id).map(n -> {
                    // Verifica se o CPF já tá vinculado naquela praia
                    if (!n.buscarCPF(n.getEsportistas(), user.getCpf())) {
                        // Verifica se a praia tem vaga
                        Boolean temVaga = n.addEsportista(user.getCpf());
                        if (temVaga) {
                            praiaRepo.save(n);
                            return "Esportista adicionado a praia " + n.getNome();
                        } else {
                            return "Praia com capacidade máxima atingida";
                        }
                    } else {
                        return "Esportista já está cadastrado";
                    }

                })
                        .orElseGet(() -> {
                            return "Praia não cadastrada";
                        });
            }
        } catch (NullPointerException e) {
            return "Esportista não cadastrado";
        }
        return null;
    }

    @DeleteMapping(path = "remover/esportista/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String delEsportista(@Valid @RequestBody UserDTO user, @PathVariable Integer id) {
        // Busca se o id da praia é valido
        return praiaRepo.findById(id).map(n -> {
            // Verifica se o CPF já tá vinculado naquela praia
            if (n.buscarCPF(n.getEsportistas(), user.getCpf())) {
                n.delEsportista(user.getCpf());
                praiaRepo.save(n);
                return "Esportista removido da praia " + n.getNome();
            } else {
                return "Esportista não está vinculado a praia " + n.getNome();
            }
        })
                .orElseGet(() -> {
                    return "Praia não cadastrada";
                });
    }

    @GetMapping(path = "/todos")
    public @ResponseBody
    Iterable<Praia> getAll() {
        return praiaRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Praia getById(@PathVariable int id) {
        return praiaRepo.findById(id).get();
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Praia updateById(@RequestBody PraiaDTO newPraia, @PathVariable int id) {
        try {
            return praiaRepo.findById(id)
                    .map(n -> {
                        n.setNome(newPraia.conversor().getNome());
                        n.setCapacidade(newPraia.conversor().getCapacidade());
                        n.getEndereco().setBairro(newPraia.conversor().getEndereco().getBairro());
                        n.getEndereco().setRua(newPraia.conversor().getEndereco().getRua());
                        n.getEndereco().setCidade(newPraia.conversor().getEndereco().getCidade());
                        n.getEndereco().setCep(newPraia.conversor().getEndereco().getCep());
                        return praiaRepo.save(n);
                    })
                    .orElseGet(() -> {
                        return praiaRepo.save(newPraia.conversor());
                    });
        } catch (Exception e) {
            return praiaRepo.save(newPraia.conversor());
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
        try {
            praiaRepo.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
