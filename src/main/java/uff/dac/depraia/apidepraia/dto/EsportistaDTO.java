package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Esportista;
import uff.dac.depraia.apidepraia.model.Agenda;

@Getter
public class EsportistaDTO {
    private Integer tipoUsuario;    
    private UserDTO user;
    private AgendaIdDTO agenda;

    public EsportistaDTO() {
    }
    
    public EsportistaDTO(Esportista n) {
        this.tipoUsuario = n.getTipoUsuario();
        this.user = new UserDTO(n.getUser());
        this.agenda = new AgendaIdDTO(n.getAgenda());
    }
            
    public Esportista conversor(Agenda n) {
        return new Esportista(tipoUsuario, user.conversor(), n);
    }
}
