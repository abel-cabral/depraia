package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.Quiosque;

@Getter
public class QuiosqueDTO {
    private String nome;
    private Praia praia;

    public QuiosqueDTO() {
    }
    
    public QuiosqueDTO(String nome, Praia praia) {
        this.nome = nome;
        this.praia = praia;
    }
    
    public Quiosque conversor() {
        return new Quiosque(nome, praia);
    }
}
