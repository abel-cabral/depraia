package uff.dac.depraia.apidepraia.dto;

import uff.dac.depraia.apidepraia.model.Praia;
import lombok.Getter;

@Getter
public class PraiaIdDTO {
    private Integer id;

    public PraiaIdDTO() {
    }

    public PraiaIdDTO(Praia p) {
        this.id = p.getId();
    }
    
    public Praia conversor(Praia p) {
        p.setId(id);
        return p;
    }    
}
