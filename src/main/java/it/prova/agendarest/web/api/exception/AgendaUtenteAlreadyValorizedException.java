package it.prova.agendarest.web.api.exception;

public class AgendaUtenteAlreadyValorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AgendaUtenteAlreadyValorizedException(String msg) {
		super(msg);
	}
}
