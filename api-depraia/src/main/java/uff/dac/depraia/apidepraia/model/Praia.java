package uff.dac.depraia.apidepraia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uff.dac.depraia.apidepraia.model.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Praia {
    private Long id;
    private int capacidade;
    private String nome;
    private Endereco endereco;
}
