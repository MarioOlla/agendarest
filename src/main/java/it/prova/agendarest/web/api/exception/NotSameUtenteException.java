package it.prova.agendarest.web.api.exception;

public class NotSameUtenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotSameUtenteException(String msg) {
		super(msg);
	}
}
