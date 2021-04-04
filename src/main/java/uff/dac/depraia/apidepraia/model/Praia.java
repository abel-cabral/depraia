package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Quiosque> quiosques;
    @ElementCollection
    private Set<String> banhistas;
    @ElementCollection
    private Set<String> esportistas;
    @ElementCollection
    private Set<String> ambulantes;
    
    public Praia() {
    }

    public Praia(Integer capacidade, String nome, Endereco endereco) {
        this.capacidade = capacidade;
        this.nome = nome;
        this.endereco = endereco;
    }
    
    public Boolean addBanhista(Banhista n) {
        return banhistas.add(n.getUser().getCpf());
    }
    
    public Boolean addEsportista(Esportista n) {
        return esportistas.add(n.getUser().getCpf());
    }
    
    public Boolean addAmbulante(Ambulante n) {
        return ambulantes.add(n.getUser().getCpf());
    }
}
