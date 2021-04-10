package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Banhista;
import uff.dac.depraia.apidepraia.model.Produto;

@Getter
public class ProdutoIdDTO {
    private Integer id;

    public ProdutoIdDTO() {
    }

    public ProdutoIdDTO(Banhista p) {
        this.id = p.getId();
    }
    
    public Produto conversor(Produto p) {
        p.setId(id);
        return p;
    }
}
