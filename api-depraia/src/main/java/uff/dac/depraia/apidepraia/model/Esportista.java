package uff.dac.depraia.apidepraia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Esportista {
    private int tipoUsuario;
    private User user;
}
