package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Produto;

@Getter
public class ProdutoIdDTO {
    private Integer id;

    public ProdutoIdDTO() {
    }
      
    public Produto conversor(Produto p) {
        p.setId(id);
        return p;
    }
}
