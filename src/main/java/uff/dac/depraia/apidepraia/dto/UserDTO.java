package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.User;

@Getter
public class UserDTO {
    private String nome;    
    private String cpf;    
    private EnderecoDTO endereco;    
    private String email;    
    private Boolean admin;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.nome = user.getNome();
        this.cpf = user.getCpf();
        this.endereco = new EnderecoDTO(user.getEndereco());
        this.email = user.getEmail();
        this.admin = user.getAdmin();
    }
            
    public User conversor() {
        return new User(nome, cpf, endereco.conversor(), email, admin);
    }
}
