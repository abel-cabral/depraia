package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Banhista;

@Getter
public class BanhistaDTO {
    private Integer tipoUsuario;    
    private UserDTO user;
    
    public Banhista conversor() {
        return new Banhista(tipoUsuario, user.conversor());
    }
}
