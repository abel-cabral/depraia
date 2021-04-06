package uff.dac.depraia.apidepraia.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uff.dac.depraia.apidepraia.model.Esportista;

public interface EsportistaRepository extends CrudRepository<Esportista, Integer>{
    @Query("SELECT r.cpf FROM User r where r.cpf = :id") 
    public String findByCPF(@Param("id") String cpf);
}
