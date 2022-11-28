package it.prova.agendarest.service;

import java.util.List;

import it.prova.agendarest.model.Agenda;

public interface AgendaService {
	
	public List<Agenda> listAll();
	
	public List<Agenda> listMine(String username);
	
	public Agenda findById(Long id, String username);
	
	public Agenda inserisciNuova(Agenda input, String username);
	
	public Agenda aggiorna(Agenda input, String username);
	
	public void delete(Long id, String username);
}
