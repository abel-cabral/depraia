package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Esportista;

@Getter
public class EsportistaIdDTO {
    private Integer id;

    public EsportistaIdDTO() {
    }

    public EsportistaIdDTO(Esportista p) {
        this.id = p.getId();
    }
    
    public Esportista conversor(Esportista p) {
        p.setId(id);
        return p;
    }
}
