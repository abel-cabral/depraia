package uff.dac.depraia.apidepraia.entities;

public class Quiosque {
    private Integer id;
    private String nome;
    private Praia praia;

    public Quiosque(String nome, Praia praia) {
        this.nome = nome;
        this.praia = praia;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Praia getPraia() {
        return praia;
    }

    public void setPraia(Praia praia) {
        this.praia = praia;
    }

}
