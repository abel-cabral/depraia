package uff.dac.depraia.apidepraia.dto;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.User;

@Getter
public class PraiaDTO {
    private Integer capacidade;
    private String nome;
    @OneToOne(cascade = CascadeType.PERSIST)
    private EnderecoDTO endereco;
    
    public Praia conversor() {
        return new Praia(capacidade, nome, endereco.conversor());
    }
}
