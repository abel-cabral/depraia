package uff.dac.depraia.apidepraia.dto;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class PraiaDTO {
    private Integer id;
    private Integer capacidade;
    private String nome;    
    private EnderecoDTO endereco;

    public PraiaDTO(Praia p) {
        this.id = p.getId();
        this.capacidade = p.getCapacidade();
        this.nome = p.getNome();
        this.endereco = new EnderecoDTO(p.getEndereco());
    }

    public PraiaDTO() {
    }
            
    public Praia conversor() {
        return new Praia(capacidade, nome, endereco.conversor());
    }
}
