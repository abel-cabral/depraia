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
            
    public User conversor() {
        return new User(nome, endereco.conversor(), email, admin);
    }
    
    public User conversor_com_cpf() {
        return new User(nome, cpf, endereco.conversor(), email, admin);
    }
}
