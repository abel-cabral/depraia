package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class AmbulanteDTO {    
    private UserDTO user;
    private PraiaIdDTO praia;

    public AmbulanteDTO() {
    }
    
    public AmbulanteDTO(Ambulante n) {        
        this.user = new UserDTO(n.getUser());
        this.praia = new PraiaIdDTO(n.getPraia());
    }
            
    public Ambulante conversor(Praia n) {
        return new Ambulante(user.conversor(), n);
    }
}
