package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.Quiosque;

@Getter
public class QuiosqueDTO {
    private String nome;
    private PraiaIdDTO praia;

    public QuiosqueDTO() {
    }
    
    public QuiosqueDTO(String nome, PraiaIdDTO praia) {
        this.nome = nome;
        this.praia = praia;
    }
    
    public Quiosque conversor(Praia p) {
        return new Quiosque(nome, p);
    }
}
