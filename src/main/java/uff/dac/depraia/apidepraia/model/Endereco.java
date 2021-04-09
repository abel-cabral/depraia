package uff.dac.depraia.apidepraia.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    //@NotBlank(message = "O campo 'rua' é obrigatório")
    private String rua;
    //@NotBlank(message = "O campo 'bairro' é obrigatório")
    private String bairro;
    //@NotBlank(message = "O campo 'cep' é obrigatório")
    private String cep;
    //@NotBlank(message = "O campo 'cidade' é obrigatório")
    private String cidade;
    
    public Endereco() {}

    public Endereco(String rua, String bairro, String cep, String cidade) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRua() {
        return this.rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
