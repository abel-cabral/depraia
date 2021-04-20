package uff.dac.depraia.apidepraia.repositories;

import org.springframework.data.repository.CrudRepository;
import uff.dac.depraia.apidepraia.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda, Integer> {
}
