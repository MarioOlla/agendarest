package it.prova.agendarest.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.agendarest.model.Ruolo;
import it.prova.agendarest.model.StatoUtente;
import it.prova.agendarest.model.Utente;

public class UtenteDTOConAgende {
	private Long id;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{email.notblank}")
	private String email;

	private Date dateCreated;

	private StatoUtente stato;

	private Long[] ruoliIds;
	
	@JsonIgnoreProperties(value = "{utente}")
	private List<AgendaDTO> agende = new ArrayList<>();

	public UtenteDTOConAgende() {
		
	}

	public UtenteDTOConAgende(Long id, String username, String nome, String cognome, StatoUtente stato, List<AgendaDTO> agende) {
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.stato = stato;
		this.agende = agende;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}
	
	public List<AgendaDTO> getAgende() {
		return agende;
	}

	public void setAgende(List<AgendaDTO> agende) {
		this.agende = agende;
	}

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = new Utente(this.id, this.username, this.password, this.nome, this.cognome, this.email,
				this.dateCreated, this.stato);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));
		result.setAgende(this.agende.stream()
				.map(agenda ->{
					return agenda.buildAgendaModel();
				})
				.collect(Collectors.toList()));
		return result;
	}

	// niente password...
	public static UtenteDTOConAgende buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTOConAgende result = new UtenteDTOConAgende(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
				utenteModel.getCognome(), utenteModel.getStato(), utenteModel.getAgende().stream()
				.map(agenda -> {
					return AgendaDTO.buildAgendaDTOFromModel(agenda);
				})
				.collect(Collectors.toList()));

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}
}
