package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Agenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    private Integer vagas;

    @JsonManagedReference
    @OneToMany(mappedBy = "agenda", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Esportista> esportistas;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "agenda", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private Set<Banhista> banhistas;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "praia_id", nullable = false)    
    private Praia praia;

    public Agenda () {}
    
    public Agenda(Praia praia, Date data) {
        this.praia = praia;
        this.data = data;
        this.vagas = praia.getCapacidade();
    }
    
    public void adicionarPessoa() throws Exception {
        if (vagas > 0) {
            vagas = vagas - 1;
        } else {
            throw new Exception("Capacidade máxima do dia atingida, verifique outra data");
        }
    }

    public void removerPessoa() {
        vagas = vagas + 1;        
    }
}
