package uff.dac.depraia.apidepraia.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uff.dac.depraia.apidepraia.model.User;

public interface LoginRepository extends CrudRepository<User, Integer> {    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.senha = :senha") 
    public Optional<User> login(@Param("email") String email, @Param("senha") String senha);
}
