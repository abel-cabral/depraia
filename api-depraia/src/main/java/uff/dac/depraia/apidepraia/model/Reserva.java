package uff.dac.depraia.apidepraia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uff.dac.depraia.apidepraia.model.Praia;
import uff.dac.depraia.apidepraia.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {
    private Long id;
    private User user;
    private Praia praia;
}
