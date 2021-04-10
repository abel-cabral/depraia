package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Banhista;

@Getter
public class BanhistaIdDTO {
    private Integer id;

    public BanhistaIdDTO() {
    }

    public BanhistaIdDTO(Banhista p) {
        this.id = p.getId();
    }
    
    public Banhista conversor(Banhista p) {
        p.setId(id);
        return p;
    }
}
