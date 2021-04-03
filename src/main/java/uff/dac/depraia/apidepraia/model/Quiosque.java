package uff.dac.depraia.apidepraia.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity

public class Quiosque implements Serializable {
    
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
   
    private String nome;
    @OneToOne(cascade=CascadeType.PERSIST)
    private Praia praia;
    
    public Quiosque () {};
    
    public Quiosque(String nome, Praia praia) {
        this.nome = nome;
        this.praia = praia;
    }
    
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Praia getPraia() {
        return this.praia;
    }

    public void setPraia(Praia praia) {
        this.praia = praia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
