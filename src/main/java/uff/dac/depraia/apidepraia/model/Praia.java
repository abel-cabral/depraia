package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
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
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Praia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String nome;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;

    @JsonManagedReference
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Agenda> agendas;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Ambulante> ambulantes;

    @JsonManagedReference
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Quiosque> quiosques;

    public Praia() {
    }

    public Praia(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public Praia(Integer id, String nome, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }
}
