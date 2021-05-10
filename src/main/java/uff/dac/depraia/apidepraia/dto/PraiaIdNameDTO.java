package uff.dac.depraia.apidepraia.dto;

import uff.dac.depraia.apidepraia.model.Praia;
import lombok.Getter;

@Getter
public class PraiaIdNameDTO {
    private Integer id;
    private String nome;

    public PraiaIdNameDTO() {
    }

    public PraiaIdNameDTO(Praia p) {
        this.id = p.getId();
        this.nome = p.getNome();
    }
    
    public Praia conversor(Praia p) {
        p.setId(id);
        p.setNome(nome);
        return p;
    }    
}
