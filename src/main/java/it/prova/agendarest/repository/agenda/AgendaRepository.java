package it.prova.agendarest.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.agendarest.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda, Long>{
	
	@Query("select a from Agenda a left join fetch a.utente u where u.username=:username")
	public List<Agenda> listAllAgendeOfUtente(String username);
	
	@Query("select a from Agenda a left join fetch a.utente u where a.id=:id")
	public Agenda findByIdEager(Long id);
}
