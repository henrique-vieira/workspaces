package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jfree.data.time.Hour;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import br.com.sistemaxm.business.CadastroCarros;
import br.com.sistemaxm.business.CadastroJornadaTransporte;
import br.com.sistemaxm.business.CadastroManutencao;
import br.com.sistemaxm.entidades.Abastecimento;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.entidades.JornadaTransporte;
import br.com.sistemaxm.entidades.Manutencao;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.enums.TipoRota;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FuncionarioRepository;
import br.com.sistemaxm.repository.JornadaTransporteRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ManutencaoRepository;

@ManagedBean
@ViewScoped
public class CadastroJornadaTransporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private JornadaTransporte jornadaTransporte;
	private Carro carro;
	private Funcionario funcionario;
	private PessoaJuridica pessoaJuridica;
	private Abastecimento abastecimento;
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Carro> carros;
	private List<Funcionario> funcionarios;
	private List<JornadaTransporte> listaJornadaTransporte;
		
	
	public CadastroJornadaTransporteBean() {
		this.jornadaTransporte = new JornadaTransporte();
		this.carro = new Carro(new Long(0), new String(), new String());
		this.funcionario = new Funcionario(new Long(0), new Pessoa());
		this.cliente = new Cliente(new Long(0), new PessoaJuridica());
		this.pessoaJuridica = new PessoaJuridica();
		this.abastecimento = new Abastecimento();

	}	
	
	public void listaJornadaTransporteInit() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			JornadaTransporteRepository jornadaTransporteRepo = new JornadaTransporteRepository(manager);
			this.listaJornadaTransporte = jornadaTransporteRepo.todasJornadasTransporte();
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}

	public List<Cliente> autoCompleteCliente(String busca) {
		List<Cliente> auxLista = new ArrayList<Cliente>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			ClienteRepository clienteRepo = new ClienteRepository(manager);
			this.clientes = clienteRepo.nomesCliente();
			for (Cliente c : clientes) {
				if (c.getCliente().getNome().toUpperCase()
						.startsWith(busca.toUpperCase())) {
					auxLista.add(c);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			manager.close();
		}
		return auxLista;

	}
	
	public void atualizaKmCarro(Carro carro) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroCarros cadastro = new CadastroCarros(new CarroRepository(manager));
			carro.setId(((Carro)manager.find(Carro.class, carro.getId())).getId());
			cadastro.editar(carro);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Km do veículo foi atualizado!", null));
		}
		catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Problema ao atualizar km do veículo", null));
		}
		finally {
			manager.close();
		}
	}
	
	public List<Funcionario> autoCompleteFuncionario(String busca) {
		List<Funcionario> auxLista = new ArrayList<Funcionario>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FuncionarioRepository funcionarioRepo = new FuncionarioRepository(manager);
			this.funcionarios = funcionarioRepo.nomesFuncionarios();
			
			for (Funcionario f : this.funcionarios) {
				if (f.getPessoa().getNome().toUpperCase().startsWith(busca.toUpperCase())) {
					auxLista.add(f);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
		
		finally {
			manager.close();
		}
		return auxLista;
	}

	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			
			for (Carro car : this.carros) {
				if ((car.getModelo().toUpperCase().startsWith(busca.toUpperCase())) 
						|| (car.getPlaca().toUpperCase().startsWith(busca.toUpperCase()))) {
							auxLista.add(car);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
		return auxLista;
	}
	
	public void onItemSelectKmInicial(SelectEvent event) {
		this.carro = (Carro) event.getObject();
		this.carro.getKm();
		this.jornadaTransporte.setKmInicial(this.carro.getKm());
	}
	
	public void atualizaKmPercorridoTotal(AjaxBehaviorEvent event) {
		this.jornadaTransporte.setKmTotalPercorrido(this.jornadaTransporte.getKmFinal()-this.jornadaTransporte.getKmInicial());
		this.jornadaTransporte.getKmTotalPercorrido();
	}
	
	public void atualizaMediaGasolinaPorLitro(AjaxBehaviorEvent event) {	
		this.abastecimento.setMedia(this.jornadaTransporte.getKmTotalPercorrido()/this.abastecimento.getLitros());
		Math.round(this.abastecimento.getMedia());
	}
	
	public void calculaHoraExtra (AjaxBehaviorEvent event) throws ParseException {
		this.jornadaTransporte.getClass();
		Calendar horaInicial = Calendar.getInstance();
		Calendar horaFinal = Calendar.getInstance();
		Calendar intervalo1 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		Date inicioJornadaDate = new Date(sdf.parse(this.jornadaTransporte.getInicioJornadaTransient()).getTime());
		Date fimJornadaDate = new Date(sdf.parse(this.jornadaTransporte.getFimJornadaTransient()).getTime());
		Date intervaloDate = new Date(sdf.parse(this.jornadaTransporte.getIntervaloTransient()).getTime());
		this.jornadaTransporte.setInicioJornada(inicioJornadaDate); 
		this.jornadaTransporte.setFimJornada(fimJornadaDate);
		this.jornadaTransporte.setIntervalo(intervaloDate);
		horaInicial.setTime(this.jornadaTransporte.getInicioJornada());
		horaFinal.setTime(this.jornadaTransporte.getFimJornada());
		intervalo1.setTime(this.jornadaTransporte.getIntervalo());
		LocalTime jornada = LocalTime.of(7,20);
		LocalTime horaInicio = LocalTime.of(horaInicial.getTime().getHours(), horaInicial.getTime().getMinutes());
		LocalTime horaFim = LocalTime.of(horaFinal.getTime().getHours(),horaFinal.getTime().getMinutes());
		LocalTime horaIntervalo = LocalTime.of(intervalo1.getTime().getHours(),intervalo1.getTime().getMinutes());
		LocalTime horaAux; 
		if(horaFim.getMinute()>horaInicio.getMinute()){
			horaAux = LocalTime.of(horaFim.getHour()-horaInicio.getHour(), horaFim.getMinute()-horaInicio.getMinute());
		}else{
			horaAux = LocalTime.of(horaFim.getHour()-horaInicio.getHour(), horaInicio.getMinute()-horaFim.getMinute());
		};
		LocalTime calculoIntervalo = LocalTime.of(horaAux.getHour()-horaIntervalo.getHour(), horaAux.getMinute()-horaIntervalo.getMinute());
		
		if (calculoIntervalo.getHour() > jornada.getHour()) {
			Calendar horaExtra = Calendar.getInstance();
			horaExtra.set(Calendar.HOUR_OF_DAY, calculoIntervalo.getHour()-jornada.getHour());
			horaExtra.set(Calendar.MINUTE, calculoIntervalo.getMinute()-jornada.getMinute());
			this.jornadaTransporte.setExtra(horaExtra.getTime().getHours()+":"+horaExtra.getTime().getMinutes());
     		this.jornadaTransporte.getExtra();
		
		}
		 this.jornadaTransporte.getExtra();
			
		
	}
	
	public List<SelectItem> getTipoItemListRota() { 
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (TipoRota rotaEnum : TipoRota.values()) {
			auxLista.add(new SelectItem(rotaEnum, rotaEnum.getValue()));
		}
		return auxLista;
	}
		

	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.jornadaTransporte.setCliente(this.cliente);
			this.jornadaTransporte.setCarro(this.carro);
			this.jornadaTransporte.setFuncionario(this.funcionario);
			if (this.abastecimento != null){
				this.jornadaTransporte.setAbastecimento(this.abastecimento);
			}
			this.carro.setKm(this.jornadaTransporte.getKmFinal());
			CadastroJornadaTransporte cadastro = new CadastroJornadaTransporte(new JornadaTransporteRepository(manager));
			cadastro.salvar(jornadaTransporte);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Jornada cadastrada com sucesso!", null));
			this.jornadaTransporte = new JornadaTransporte();
			atualizaKmCarro(carro);
			
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar Jornada.", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.jornadaTransporte = (JornadaTransporte) event.getObject();
		
		try {
			trx.begin();
			CadastroJornadaTransporte cadastro = new CadastroJornadaTransporte(
					new JornadaTransporteRepository(manager));

			this.jornadaTransporte
					.setCodigo(((JornadaTransporte) manager.find(JornadaTransporte.class,
							this.jornadaTransporte.getCodigo())).getCodigo());
			cadastro.editar(this.jornadaTransporte);
			trx.commit();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Registro alterado com sucesso!", null));
			this.jornadaTransporte = new JornadaTransporte();

		} catch (Exception e) {
			trx.rollback();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"registro não alterado!", null));
		} finally {
			manager.close();			
		}
	}

	public JornadaTransporte getJornadaTransporte() {
		return jornadaTransporte;
	}

	public void setJornadaTransporte(JornadaTransporte jornadaTransporte) {
		this.jornadaTransporte = jornadaTransporte;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}


	public List<JornadaTransporte> getListaJornadaTransporte() {
		return listaJornadaTransporte;
	}


	public void setListaJornadaTransporte(List<JornadaTransporte> listaJornadaTransporte) {
		this.listaJornadaTransporte = listaJornadaTransporte;
	}

	public Abastecimento getAbastecimento() {
		return abastecimento;
	}

	public void setAbastecimento(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
	}

}
