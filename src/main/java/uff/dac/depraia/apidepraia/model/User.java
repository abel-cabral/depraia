package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;    
    private String nome;    
    private String cpf;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;    
    private String email;
    private String senha;
        
    private Boolean admin = false;
    private Integer tipoUsuario;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "usuarios")
    private Set<Agenda> agendas;
    
    public User() {}
            
    public User(String nome, String cpf, Endereco endereco, String email, Integer tipoUsuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }
    
    public User(String nome, String cpf, Endereco endereco, String email, Integer tipoUsuario, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }
}
