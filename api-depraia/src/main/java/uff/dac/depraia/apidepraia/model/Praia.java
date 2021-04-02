package uff.dac.depraia.apidepraia.model;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Praia {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int capacidade;
    private String nome;
    private Endereco endereco;
    
    public Praia () {}
    
    public Praia(Integer capacidade, Endereco endereco) {
        this.capacidade = capacidade;
        this.endereco = endereco;
    }   
}
