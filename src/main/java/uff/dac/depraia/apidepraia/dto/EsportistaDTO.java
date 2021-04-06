package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Esportista;

@Getter
public class EsportistaDTO {
    private Integer tipoUsuario;    
    private UserDTO user;

    public EsportistaDTO() {
    }
            
    public Esportista conversor() {
        return new Esportista(tipoUsuario, user.conversor_com_cpf());
    }
}
