package it.prova.agendarest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.model.Utente;
import it.prova.agendarest.repository.agenda.AgendaRepository;
import it.prova.agendarest.repository.utente.UtenteRepository;
import it.prova.agendarest.web.api.exception.AgendaUtenteAlreadyValorizedException;
import it.prova.agendarest.web.api.exception.AgendaWithoutUtenteException;
import it.prova.agendarest.web.api.exception.ElementNotFoundException;
import it.prova.agendarest.web.api.exception.IdNotNullForInsertException;
import it.prova.agendarest.web.api.exception.NotSameUtenteException;

@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;
	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public List<Agenda> listAll() {
			return (List<Agenda>) agendaRepository.findAll();
	}

	@Override
	public Agenda findById(Long id, String username) {
		Utente curr = utenteRepository.findByUsername(username).orElse(null);
		Agenda result = agendaRepository.findByIdEager(id);
		if (result.getUtente().isAdmin())
			return result;
		else if(result.getUtente().getId()==curr.getId())
			return result;
		else throw new NotSameUtenteException("The current user and the Agenda's user are not the same.");
	}

	@Override
	public Agenda inserisciNuova(Agenda input, String username) {
		Utente curr = utenteRepository.findByUsername(username).orElse(null);
		if(curr == null)
			throw new ElementNotFoundException("Couldn't find Utente with username:"+username);
		if (input.getId() != null)  
			throw new IdNotNullForInsertException("Cannot insert this Agenda, the id field is not null");
		if(input.getUtente() != null)
			throw new AgendaUtenteAlreadyValorizedException("The utente field cannot be null");
		input.setUtente(utenteRepository.findByUsername(username).orElse(null));
		return agendaRepository.save(input);
	}

	@Override
	public Agenda aggiorna(Agenda input, String username) {
		if(input.getUtente() == null)
			throw new AgendaWithoutUtenteException("This agenda does not have an utente");
		if (!input.getUtente().getUsername().equals(username))
			throw new NotSameUtenteException("The current user and the Agenda's user are not the same.");
		input.setUtente(utenteRepository.findByUsername(username).orElse(null));
		return agendaRepository.save(input);
	}

	@Override
	public void delete(Long id, String username) {
		Agenda result = agendaRepository.findByIdEager(id);
		if (result.getUtente().getUsername().equals(username))
			agendaRepository.deleteById(id);
		else
			throw new NotSameUtenteException("The current user and the Agenda's user are not the same.");
	}

	@Override
	public List<Agenda> listMine(String username) {
		return agendaRepository.listAllAgendeOfUtente(username);
	}

}
