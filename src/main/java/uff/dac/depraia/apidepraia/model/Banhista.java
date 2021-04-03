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
public class Banhista implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@NotNull(message = "O campo 'tipoUsuario' é obrigatório")
    private Integer tipoUsuario;
    @OneToOne(cascade=CascadeType.PERSIST)
    private User user;

    public Banhista () {}
    
    public Banhista(Integer tipoUsuario, User user) {
        this.tipoUsuario = tipoUsuario;
        this.user = user;
    }   
    
    public Integer getTipoUsuario() {
        return this.tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
