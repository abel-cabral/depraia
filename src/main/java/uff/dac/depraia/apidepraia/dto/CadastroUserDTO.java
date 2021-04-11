package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.User;

@Getter
public class CadastroUserDTO {      
    private String nome;    
    private String cpf;    
    private EnderecoDTO endereco;    
    private String email;    
    private Integer tipoUsuario;
    private String senha;

    public CadastroUserDTO() {
    }

    public CadastroUserDTO(User user) {
        this.nome = user.getNome();
        this.cpf = user.getCpf();
        this.endereco = new EnderecoDTO(user.getEndereco());
        this.email = user.getEmail();
        this.senha = user.getSenha();
    }
            
    public User conversor() {
        return new User(nome, cpf, endereco.conversor(), email, tipoUsuario, senha);
    }
}
