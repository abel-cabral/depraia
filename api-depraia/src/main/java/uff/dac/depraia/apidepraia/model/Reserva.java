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
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade=CascadeType.PERSIST)
    private User user;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Praia praia;

    public Reserva () {}
    
    public Reserva(User user, Praia praia) {
        this.user = user;
        this.praia = praia;
    }   

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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
