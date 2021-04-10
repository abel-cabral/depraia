package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class PraiaDTO {    
    private String nome;    
    private EnderecoDTO endereco;

    public PraiaDTO(Praia p) {        
        this.nome = p.getNome();
        this.endereco = new EnderecoDTO(p.getEndereco());
    }

    public PraiaDTO() {
    }
            
    public Praia conversor() {
        return new Praia(nome, endereco.conversor());
    }
}


