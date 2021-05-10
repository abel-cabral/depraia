package uff.dac.depraia.apidepraia.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uff.dac.depraia.apidepraia.model.Ambulante;
import uff.dac.depraia.apidepraia.model.User;

public interface AmbulanteRepository extends CrudRepository<Ambulante, Integer>{
    @Query("SELECT a FROM Ambulante a WHERE a.user = :user") 
    public Optional<Ambulante> findByUserId(@Param("user") User id);
}
