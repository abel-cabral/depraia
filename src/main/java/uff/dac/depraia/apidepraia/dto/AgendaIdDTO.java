package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Agenda;


@Getter
public class AgendaIdDTO {  
    private Integer id;

    public AgendaIdDTO() {
    }

    public AgendaIdDTO(Agenda p) {
        this.id = p.getId();
    }
    
    public Agenda conversor(Agenda p) {
        p.setId(id);
        return p;
    }    
}
