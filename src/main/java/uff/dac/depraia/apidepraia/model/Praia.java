package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Praia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer capacidade;
    private String nome;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Agenda> agendas;

    @JsonManagedReference
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Quiosque> quiosques;
    
    public Praia() {
    }

    public Praia(Integer capacidade, String nome, Endereco endereco) {
        this.capacidade = capacidade;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Praia(Integer capacidade, Integer id, String nome, Endereco endereco) {
        this.id = id;
        this.capacidade = capacidade;
        this.nome = nome;
        this.endereco = endereco;
    }
}
