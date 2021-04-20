package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class CadastroDTO {   
    private UserDTO user;
    private AgendaIdDTO agenda;
    private PraiaIdDTO praia;

    public CadastroDTO() {
    }
        
    public Ambulante conversorAmbulante(Praia n) {
        return new Ambulante(user.conversor(), n);
    }
}
