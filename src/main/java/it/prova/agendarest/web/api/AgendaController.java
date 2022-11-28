package it.prova.agendarest.web.api;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agendarest.dto.AgendaDTO;
import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.service.AgendaServiceImpl;
import it.prova.agendarest.web.api.exception.ElementNotFoundException;
import it.prova.agendarest.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaServiceImpl agendaService;
	
	@GetMapping("/listAll")
	public List<AgendaDTO> listAll(){
		return agendaService.listAll().stream()
				.map(agenda -> {
					return AgendaDTO.buildAgendaDTOFromModel(agenda);
				}).collect(Collectors.toList());
	}
	
	@GetMapping
	public List<AgendaDTO> listMine(Principal principal){
		return agendaService.listMine(principal.getName()).stream()
				.map(agenda -> {
					return AgendaDTO.buildAgendaDTOFromModel(agenda);
				}).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(name = "id", required = true) Long id, Principal principal) {
		return AgendaDTO.buildAgendaDTOFromModel(agendaService.findById(id, principal.getName()));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AgendaDTO inserisciNuovo(@Valid@RequestBody AgendaDTO input, Principal principal) {
		if(input.getId()!=null)
			throw new IdNotNullForInsertException("Cannot insert, this Agenda already has an id:"+input.getId());
		System.out.println(input.getDescrizione()+input.getDataOraInizio()+input.getDataOraFine()+(input.getUtente()==null));
		Agenda nuova = input.buildAgendaModel();
		nuova = agendaService.inserisciNuova(nuova, principal.getName());
		return AgendaDTO.buildAgendaDTOFromModel(nuova);
	}
	
	@PutMapping("/{id}")
	public AgendaDTO aggiorna(@Valid@RequestBody AgendaDTO input,@PathVariable(name = "id", required = true) Long id, Principal principal) {
		if(agendaService.findById(id, null) == null)
			throw new ElementNotFoundException("Couldn't find Agenta with id:"+id);
		Agenda aggiornata = input.buildAgendaModel();
		aggiornata = agendaService.aggiorna(aggiornata, principal.getName());
		return AgendaDTO.buildAgendaDTOFromModel(aggiornata);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(name = "id", required = true) Long id, Principal principal) {
		agendaService.delete(id, principal.getName());
	}
}