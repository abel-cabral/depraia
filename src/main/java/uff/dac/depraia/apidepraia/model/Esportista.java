package uff.dac.depraia.apidepraia.model;

import java.io.Serializable;
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
public class Esportista implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@NotNull(message = "O campo 'tipoUsuario' é obrigatório")
    private Integer tipoUsuario;
    @OneToOne(cascade=CascadeType.PERSIST)
    private User user;

    public Esportista(Integer tipoUsuario, User user) {
        this.tipoUsuario = tipoUsuario;
        this.user = user;
    }
    
    public Esportista() {}
}
