package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Banhista;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.Esportista;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class CadastroDTO {   
    private CadastroUserDTO user;
    private AgendaIdDTO agenda;
    private PraiaIdDTO praia;

    public CadastroDTO() {
    }
    
    public CadastroDTO(Banhista n) {
        this.user = new CadastroUserDTO(n.getUser());
        this.agenda = new AgendaIdDTO(n.getAgenda());
    }
            
    public Banhista conversorBanhita(Agenda n) {
        return new Banhista(user.conversor(), n);
    }
    
    public Esportista conversorEsportista(Agenda n) {
        return new Esportista(user.conversor(), n);
    }
    
    public Ambulante conversorAmbulante(Praia n) {
        return new Ambulante(user.conversor(), n);
    }
}
