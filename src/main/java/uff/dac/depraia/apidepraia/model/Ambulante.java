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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Ambulante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "praia_id", nullable = false)
    private Praia praia;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ambulante_produto",
            joinColumns = {
                @JoinColumn(name = "ambulante_id", referencedColumnName = "id",
                        nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "produto_id", referencedColumnName = "id",
                        nullable = false, updatable = false)})
    private Set<Produto> produtos;

    public Ambulante() {
    }

    public Ambulante(User user, Praia praia) {        
        this.user = user;
        this.praia = praia;
    }
    
    public Ambulante(Integer id, User user, Praia praia, Set<Produto> produtos) {
        this.id = id;
        this.user = user;
        this.praia = praia;
        this.produtos = produtos;
    }
}
