package uff.dac.depraia.apidepraia.entities;

import uff.dac.depraia.apidepraia.model.Produto;
import uff.dac.depraia.apidepraia.model.User;
import java.util.ArrayList;
import java.util.List;

public class Ambulante extends User {

    private Integer tipoUsuario;
    List<Produto> targetList = new ArrayList<>();

    public Ambulante(Integer tipoUsuario, User user) {
        super(user.getNome(), user.getCpf(), user.getEndereco(), user.getEmail(), user.isAdmin());
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<Produto> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<Produto> targetList) {
        this.targetList = targetList;
    }

    
}
