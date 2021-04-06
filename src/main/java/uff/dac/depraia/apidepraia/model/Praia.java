package uff.dac.depraia.apidepraia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Praia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int capacidade;
    private String nome;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;
    @OneToMany(mappedBy = "praia", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Quiosque> quiosques;
    @ElementCollection
    private Set<String> banhistas;
    @ElementCollection
    private Set<String> esportistas;
    @ElementCollection
    private Set<String> ambulantes;

    public Praia() {
    }

    public Praia(Integer capacidade, String nome, Endereco endereco) {
        this.capacidade = capacidade;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Boolean addBanhista(String cpf) {
        if (getCapacidade() >= 1) {
            setCapacidade(getCapacidade() - 1);
            return banhistas.add(cpf);
        }
        return false;
    }

    public Boolean addEsportista(String cpf) {
        if (getCapacidade() >= 1) {
            setCapacidade(getCapacidade() - 1);
            return esportistas.add(cpf);
        }
        return false;
    }

    public Boolean addAmbulante(String cpf) {
        if (getCapacidade() >= 1) {
            setCapacidade(getCapacidade() - 1);
            return ambulantes.add(cpf);
        }
        return false;
    }

    public Boolean delBanhista(String cpf) {
        setCapacidade(getCapacidade() + 1);
        return banhistas.remove(cpf);        
    }

    public Boolean delEsportista(String cpf) {
        setCapacidade(getCapacidade() + 1);
            return esportistas.remove(cpf);
    }

    public Boolean delAmbulante(String cpf) {
        setCapacidade(getCapacidade() + 1);
        return ambulantes.remove(cpf);
    }

    public Boolean buscarCPF(Set<String> set, String cpf) {
        for(String s : set) {
            if (s.equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public Set<Quiosque> getQuiosques() {
        return quiosques;
    }

    public void setQuiosques(Set<Quiosque> quiosques) {
        this.quiosques = quiosques;
    }

    public Set<String> getBanhistas() {
        return banhistas;
    }

    public void setBanhistas(Set<String> banhistas) {
        this.banhistas = banhistas;
    }

    public Set<String> getEsportistas() {
        return esportistas;
    }

    public void setEsportistas(Set<String> esportistas) {
        this.esportistas = esportistas;
    }

    public Set<String> getAmbulantes() {
        return ambulantes;
    }

    public void setAmbulantes(Set<String> ambulantes) {
        this.ambulantes = ambulantes;
    }
    
    
}
