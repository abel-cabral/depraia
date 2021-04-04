
package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Endereco;

@Getter
public class EnderecoDTO {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;

    public EnderecoDTO(Endereco endereco) {
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cep = endereco.getCep();
        this.cidade = endereco.getCidade();
    }

    public EnderecoDTO() {
    }
            
    public Endereco conversor() {
        return new Endereco(rua, bairro, cep, cidade);
    }
}
