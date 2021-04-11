package uff.dac.depraia.apidepraia.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;    
    private String nome;    
    private String cpf;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;    
    private String email;
    private String senha;
        
    private Boolean admin = false;
    private Integer tipoUsuario; 
    
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
