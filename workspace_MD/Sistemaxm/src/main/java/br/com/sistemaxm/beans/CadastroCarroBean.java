package br.com.sistemaxm.beans;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroCarros;
import br.com.sistemaxm.business.CadastroMarcas;
import br.com.sistemaxm.entidades.CalendarioLicenciamento;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.enums.AcessorioEnum;
import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.enums.Combustivel;
import br.com.sistemaxm.repository.CalendarioLicenciamentoRepository;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@ViewScoped
public class CadastroCarroBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Carro carro;	
	private Proprietario proprietario;
	private List<Marca> marcas;
	private List<Carro> carros;
	private List<Proprietario> listaProprietarios;
	private List<CalendarioLicenciamento> calendarioLicenciamento;
	
	public CadastroCarroBean() {
		this.carro = new Carro();
		this.proprietario = new Proprietario(new Long(0), new Pessoa());
	}
		
	@PostConstruct
	public void prepararCadastro() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			MarcaRepository marcaRepo = new MarcaRepository(manager);
			this.marcas = marcaRepo.todos();
		}
		finally {
			manager.close();
		}
	}
	
	public void buscaLicenciamento() {
		char finalPlaca = this.carro.getPlaca().charAt(this.carro.getPlaca().length()-1);
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CalendarioLicenciamentoRepository calendarioLicenciamentoRepo = new CalendarioLicenciamentoRepository(manager);
			this.carro.setCalendarioLicenciamento(calendarioLicenciamentoRepo.buscaLicenciamentoPorPlaca(finalPlaca));
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}
	
	// Método que faz a busca do licenciamento
	/*public String buscaLicenciamento() {
		char finalPlaca = this.carro.getPlaca().charAt(this.carro.getPlaca().length()-1);
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CalendarioLicenciamentoRepository calendarioLicenciamentoRepo = new CalendarioLicenciamentoRepository(manager);
			this.calendarioLicenciamento = calendarioLicenciamentoRepo.buscaLicenciamentoPorPlaca(finalPlaca);
			Calendar c = Calendar.getInstance();
			Date dataVencimentoLicenciamento = null;
			String dtVencString = null;
			for (CalendarioLicenciamento cl : this.calendarioLicenciamento) {
				c.set(c.get(Calendar.YEAR), cl.getMesVencimento(), cl.getDiaVencimento());
				dataVencimentoLicenciamento = c.getTime();				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				dtVencString = df.format(dataVencimentoLicenciamento);	
				
			}
			return dtVencString;
			
		}
		catch(Exception e) {
			
		}
		finally {
			manager.close();
		}
		return null;
	}
	*/
	
	public List<SelectItem> getTipoItemListCambio() { 
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (Cambio cambioEnum : Cambio.values()) {
			auxLista.add(new SelectItem(cambioEnum, cambioEnum.getValue()));
		}
		return auxLista;
	}
	
	
	public List<SelectItem> getTipoItemListCombustivel() {
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (Combustivel combustivelEnum : Combustivel.values()) {
			auxLista.add(new SelectItem(combustivelEnum, combustivelEnum.getValue()));
		}
		return auxLista;
	}


	public Cambio[] getCambio() {
		return Cambio.values();
	}
	
	public Combustivel[] getCombustivel() {
		return Combustivel.values();
	}
	
	public String getFieldUpperCase(String field) {
		return field.toUpperCase();
	}
	
	public List<Proprietario> autoCompleteProprietario(String busca) {
		List<Proprietario> auxLista = new ArrayList<Proprietario>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {			
			ProprietarioRepository proprietarioRepo = new ProprietarioRepository(manager);
			this.listaProprietarios = proprietarioRepo.nomesProprietario();
			
			for (Proprietario p : listaProprietarios) {
				if (p.getProprietario().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(p);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
				
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();  
			CadastroCarros cadastro = new CadastroCarros(new CarroRepository(manager));		
			this.carro.setPlaca(getFieldUpperCase(this.carro.getPlaca()));
			this.carro.setUf(getFieldUpperCase(this.carro.getUf()));
			this.carro.setProprietario(proprietario);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			buscaLicenciamento();
			//this.carro.setVencLicenciamento(dateFormat.parse(buscaLicenciamento()));
			cadastro.salvar(carro);			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Veiculo cadastrado com sucesso!",null));
			this.carro = new Carro();
			trx.commit();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar veiculo, verifique se já existe registro com essa placa ou se os campos obrigatórios estão preenchidos.", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void excluir() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroCarros cadastro = new CadastroCarros(new CarroRepository(manager));
			carro = manager.find(Carro.class, carro.getId());
			cadastro.excluir(carro);
			this.carro = new Carro();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Veículo excluido com sucesso!",null));
		}
		catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao excluir veículo, verifique se o veículo não possui vínculo com jornadas ou manutenções!",null));
		}
		finally {
			manager.close();
		}
	}
	
	public List<Marca> getMarcas() {
		return marcas;
	}
	
	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}


	public List<Carro> getCarros() {
		return carros;
	}


	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}


	public List<Proprietario> getListaProprietarios() {
		return listaProprietarios;
	}


	public void setListaProprietarios(List<Proprietario> listaProprietarios) {
		this.listaProprietarios = listaProprietarios;
	}


	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public List<CalendarioLicenciamento> getCalendarioLicenciamento() {
		return calendarioLicenciamento;
	}

	public void setCalendarioLicenciamento(
			List<CalendarioLicenciamento> calendarioLicenciamento) {
		this.calendarioLicenciamento = calendarioLicenciamento;
	}	
	
	
	
}
