package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.User;

@Getter
public class LoginDTO {    
    private String email;    
    private String senha;

    public LoginDTO() {
    }

    public LoginDTO(User user) {        
        this.email = user.getEmail();
        this.senha = user.getSenha();        
    }
}
