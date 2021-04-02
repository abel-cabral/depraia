package uff.dac.depraia.apidepraia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiosque {
    private Long id;
    private String nome;
    private Praia praia;
}
