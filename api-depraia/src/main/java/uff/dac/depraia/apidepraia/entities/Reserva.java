package uff.dac.depraia.apidepraia.entities;

import uff.dac.depraia.apidepraia.model.User;

public class Reserva {

    private Integer id;
    private User user;
    private Praia praia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Praia getPraia() {
        return praia;
    }

    public void setPraia(Praia praia) {
        this.praia = praia;
    }
}
