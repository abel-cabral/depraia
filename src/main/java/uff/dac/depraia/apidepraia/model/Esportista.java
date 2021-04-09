package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Esportista implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    //@NotNull(message = "O campo 'tipoUsuario' é obrigatório")
    private Integer tipoUsuario;
    
    @OneToOne(cascade=CascadeType.PERSIST)
    private User user;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "praia_id", nullable = false)
    private Praia praia;

    public Esportista(Integer tipoUsuario, Praia praia, User user) {
        this.tipoUsuario = tipoUsuario;
        this.user = user;
        this.praia = praia;
    }
    
    public Esportista() {}
}
