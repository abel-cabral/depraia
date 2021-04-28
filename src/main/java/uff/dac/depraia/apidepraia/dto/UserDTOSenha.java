package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.User;

@Getter
public class UserDTOSenha {
    private Integer id;
    private String nome;    
    private String cpf;    
    private EnderecoDTO endereco;    
    private String email;    
    private Integer tipoUsuario;
    private Boolean admin;
    private String senha;

    public UserDTOSenha() {
    }

    public UserDTOSenha(User user) {
        this.id = user.getId();
        this.admin = user.getAdmin();
        this.nome = user.getNome();
        this.cpf = user.getCpf();        
        this.endereco = new EnderecoDTO(user.getEndereco());
        this.email = user.getEmail();
        this.tipoUsuario = user.getTipoUsuario();
        this.senha =  user.getSenha();
    }
            
    public User conversor() {
        return new User(nome, cpf, endereco.conversor(), email, tipoUsuario);
    }
}
