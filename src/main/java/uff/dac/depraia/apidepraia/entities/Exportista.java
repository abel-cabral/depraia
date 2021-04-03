package uff.dac.depraia.apidepraia.entities;

import uff.dac.depraia.apidepraia.model.User;
import uff.dac.depraia.apidepraia.model.Endereco;

public class Exportista extends User {

    private Integer tipoUsuario;
    private User user;

    public Exportista(Integer tipoUsuario, User user, String nome, String cpf, Endereco endereco, String email, boolean admin) {
        super(nome, cpf, endereco, email, admin);
        this.tipoUsuario = tipoUsuario;
        this.user = user;
    }   

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
