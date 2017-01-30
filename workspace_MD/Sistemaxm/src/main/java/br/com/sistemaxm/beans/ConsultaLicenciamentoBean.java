package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import org.primefaces.event.SelectEvent;

import br.com.sistemaxm.entidades.CalendarioLicenciamento;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.repository.CalendarioLicenciamentoRepository;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaLicenciamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CalendarioLicenciamento calendarioLicenciamento;	
	private List<Carro> carros;
	List<Integer> listaMeses;

	public ConsultaLicenciamentoBean() {
		this.calendarioLicenciamento = new CalendarioLicenciamento();
	}
	
	public List<Integer> autoCompleteCalendarioLicenciamento(int busca) {
		listaMeses = new ArrayList<Integer>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CalendarioLicenciamentoRepository calendarioLicenciamentoRepo = new CalendarioLicenciamentoRepository(manager);			
			
			for (Integer i : calendarioLicenciamentoRepo.mesVencimento()) {				
				listaMeses.add(i);				
			}
		}
		finally {
			manager.close();
		}
		return listaMeses;
		
	}
	
	public void onItemSelect(SelectEvent event) {
		int mesVencimento = (Integer) event.getObject();
		listaConsultaLicenciamentoDt(mesVencimento);
	}
	
	//Método que busca os veículos a partir do mÊs passado no parâmetro
	public void listaConsultaLicenciamentoDt(int mesVencimento) {		
		//char finalPlaca = this.carro.getPlaca().charAt(this.carro.getPlaca().length()-1);
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.carrosLicenciamento(mesVencimento);
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}

	public CalendarioLicenciamento getCalendarioLicenciamento() {
		return calendarioLicenciamento;
	}

	public void setCalendarioLicenciamento(CalendarioLicenciamento calendarioLicenciamento) {
		this.calendarioLicenciamento = calendarioLicenciamento;
	}

	public List<Carro> getCarros() {
		return this.carros;
	}
	
	public void setCarros(List<Carro> carros){
		this.carros = carros;
	}
}
