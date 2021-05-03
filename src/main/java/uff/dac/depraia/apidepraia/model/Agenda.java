package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Agenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    private Integer vagas;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany
    @JoinTable(name = "agenda_user", joinColumns = {
        @JoinColumn(name = "agenda_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id")})
    private Set<User> usuarios;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "praia_id", nullable = false)
    private Praia praia;

    public Agenda() {
    }

    public Agenda(Praia praia, Date data, Integer vagas) {
        this.praia = praia;
        this.data = data;
        this.vagas = vagas;
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
