package it.prova.agendarest.web.api.exception;

public class AgendaWithoutUtenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AgendaWithoutUtenteException(String msg) {
		super(msg);
	}
}
