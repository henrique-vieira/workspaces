package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@RequestScoped
public class RelatorioManutencaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataCotacaoInicio;
	private Date pDataCotacaoFim;
	private List<Servico> servicos;
	private List<ItemPeca> itemPecas;
	private List<Carro> carros;
	private Carro carro;
	private ItemPeca itemPeca;
	private Servico servico;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public RelatorioManutencaoBean() {
	
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			CarroRepository carroRepo = new CarroRepository(
					manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				if (c.getModelo().toLowerCase().startsWith(busca.toLowerCase()) 
						|| c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())) {
				auxLista.add(c);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}

	public List<ItemPeca> autoCompleteItemPeca(String busca) {
		List<ItemPeca> auxLista = new ArrayList<ItemPeca>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			ItemPecaRepository itemPecaRepo = new ItemPecaRepository(
					manager);
			this.itemPecas = itemPecaRepo.itensCombo();
			for (ItemPeca i : itemPecas) {
				if (i.getDescricao().toLowerCase().startsWith(busca.toLowerCase())) {
				auxLista.add(i);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}
	
	public List<Servico> autoCompleteServico(String busca) {
		List<Servico> auxLista = new ArrayList<Servico>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			ServicoRepository servicoRepo = new ServicoRepository(
					manager);
			this.servicos = servicoRepo.todos();
			for (Servico s : servicos) {
				if (s.getDescricao().toLowerCase().startsWith(busca.toLowerCase())) {
				auxLista.add(s);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.pDataCotacaoInicio);
		parametros.put("data_fim", this.pDataCotacaoFim);
		
		if (this.carro != null) {
			parametros.put("pCarro", "AND c.placa = '" + this.carro.getPlaca() + "'");
		}
		
		if (this.itemPeca != null) {
			parametros.put("pPeca", "AND ip.descricao = '" + this.itemPeca.getDescricao() + "'");
		}
		
		if (this.servico != null) {
			parametros.put("pServico", "AND s.descricao = '" + this.servico.getDescricao() + "'");
		}
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioDeManutencao.jasper", 
				this.response, parametros, "Relatorio Manutencao.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();	
	}

	public Date getpDataCotacaoInicio() {
		return pDataCotacaoInicio;
	}

	public void setpDataCotacaoInicio(Date pDataCotacaoInicio) {
		this.pDataCotacaoInicio = pDataCotacaoInicio;
	}

	public Date getpDataCotacaoFim() {
		return pDataCotacaoFim;
	}

	public void setpDataCotacaoFim(Date pDataCotacaoFim) {
		this.pDataCotacaoFim = pDataCotacaoFim;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<ItemPeca> getItemPecas() {
		return itemPecas;
	}

	public void setItemPecas(List<ItemPeca> itemPecas) {
		this.itemPecas = itemPecas;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	
	
}
