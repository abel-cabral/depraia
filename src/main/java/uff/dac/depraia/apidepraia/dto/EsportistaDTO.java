package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Esportista;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class EsportistaDTO {
    private Integer tipoUsuario;    
    private UserDTO user;
    private PraiaIdDTO praia;

    public EsportistaDTO() {
    }
            
    public Esportista conversor(Praia p) {
        return new Esportista(tipoUsuario, praia.conversor(p), user.conversor());
    }
}
