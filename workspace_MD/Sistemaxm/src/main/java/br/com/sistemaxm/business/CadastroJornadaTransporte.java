package br.com.sistemaxm.business;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.entidades.JornadaTransporte;
import br.com.sistemaxm.repository.JornadaTransporteRepository;
import br.com.sistemaxm.repository.JpaUtil;

public class CadastroJornadaTransporte implements Serializable {

	private static final long serialVersionUID = 1L;

	JornadaTransporteRepository jornadaTransporteRepository;
	
	public CadastroJornadaTransporte(JornadaTransporteRepository jornadaTransporteRepository) {
		this.jornadaTransporteRepository = jornadaTransporteRepository;
	}

	public CadastroJornadaTransporte() {
		
	}
	
	public void salvar(JornadaTransporte jornadaTransporte) {
		this.jornadaTransporteRepository.salvar(jornadaTransporte);
	}
	
	public void excluir(JornadaTransporte jornadaTransporte) {
		this.jornadaTransporteRepository.excluir(jornadaTransporte);
	}
	
	public void editar(JornadaTransporte jornadaTransporte) {
		this.jornadaTransporteRepository.editar(jornadaTransporte);
	}
}
