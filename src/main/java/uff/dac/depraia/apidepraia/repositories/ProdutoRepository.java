package uff.dac.depraia.apidepraia.repositories;

import org.springframework.data.repository.CrudRepository;
import uff.dac.depraia.apidepraia.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
    
}
