package uff.dac.depraia.apidepraia.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ambulante")
public class Ambulante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="vendedor_venda", 
                joinColumns={@JoinColumn(name="ambulante_id")}, 
                inverseJoinColumns={@JoinColumn(name="produto_id")})
    private Set<Produto> produtos;

    public Ambulante() {
    }

    public Ambulante(User user) {
        this.user = user;
    }
}
