package uff.dac.depraia.apidepraia.dto;

import java.util.Set;
import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.Produto;

@Getter
public class AmbulanteDTO {
    private Integer id;    
    private UserDTO user;
    private PraiaIdDTO praia;
    private Set<Produto> produtos;

    public AmbulanteDTO() {
    }

    public AmbulanteDTO(Ambulante ambulante) {
        this.id = ambulante.getId();
        this.user = new UserDTO(ambulante.getUser());
        this.praia = new PraiaIdDTO(ambulante.getPraia());
        this.produtos = ambulante.getProdutos();
    }   
                
    public Ambulante conversor(Praia n) {
        return new Ambulante(user.conversor(), n);
    }
}
