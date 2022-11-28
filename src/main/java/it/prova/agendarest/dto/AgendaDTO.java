package it.prova.agendarest.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.agendarest.model.Agenda;

public class AgendaDTO {

	private Long id;
	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;
	@NotNull(message = "{dataOraInizio.notnull}")
	private LocalDateTime dataOraInizio;
	@NotNull(message = "{dataOraFine.notnull}")
	private LocalDateTime dataOraFine;
	@JsonIgnoreProperties(value = "{agende}")
	private UtenteDTO utente;

	public AgendaDTO() {

	}

	public AgendaDTO(Long id, String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine,
			UtenteDTO utente) {
		this.id = id;
		this.descrizione = descrizione;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
		this.utente = utente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOraInizio() {
		return dataOraInizio;
	}

	public void setDataOraInizio(LocalDateTime dataOraInizio) {
		this.dataOraInizio = dataOraInizio;
	}

	public LocalDateTime getDataOraFine() {
		return dataOraFine;
	}

	public void setDataOraFine(LocalDateTime dataOraFine) {
		this.dataOraFine = dataOraFine;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	public Agenda buildAgendaModel() {
		return new Agenda(this.id, this.descrizione, this.dataOraInizio, this.dataOraFine, null);
	}
	
	public static AgendaDTO buildAgendaDTOFromModel(Agenda model) {
		return new AgendaDTO(model.getId(), model.getDescrizione(), model.getDataOraInizio(), model.getDataOraFine(), UtenteDTO.buildUtenteDTOFromModel(model.getUtente()));
	}
}
