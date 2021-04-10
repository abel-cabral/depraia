package uff.dac.depraia.apidepraia.dto;

import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Produto;

@Getter
public class ProdutoDTO {
    private String nome;
    private String descricao;
    private Double preco;    

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
            
    public Produto conversor() {
        return new Produto(nome, descricao, preco);
    }
}
