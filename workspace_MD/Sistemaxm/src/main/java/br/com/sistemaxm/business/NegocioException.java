package br.com.sistemaxm.business;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1l;
	
	public NegocioException(String msg) {
		super(msg);
	}
}
