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
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;
    //@NotBlank(message = "O campo cpf' é obrigatório")
    private String cpf;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;
    //@NotBlank(message = "O campo 'email' é obrigatório")
    private String email;    
    private Boolean admin;
    
    public User() {}
    
    public User(String nome, Endereco endereco, String email, Boolean admin) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.admin = admin;
    }
    
    public User(String nome, String cpf, Endereco endereco, String email, Boolean admin) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.admin = admin;
    }
}
