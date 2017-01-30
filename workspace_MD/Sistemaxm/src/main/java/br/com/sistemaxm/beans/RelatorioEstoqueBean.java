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

import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@RequestScoped
public class RelatorioEstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataCotacaoInicio;
	private Date pDataCotacaoFim;
	private List<ItemPeca> itemPecas;
	private ItemPeca itemPeca;
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public RelatorioEstoqueBean() {
		
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
	
	public List<Fornecedor> autoCompleteFornecedor(String busca) {
		List<Fornecedor> auxLista = new ArrayList<Fornecedor>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FornecedorRepository fornecedorRepo = new FornecedorRepository(manager);
			this.fornecedores = fornecedorRepo.nomesFornecedor();
			for (Fornecedor f : fornecedores) {
				if (f.getFornecedor().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(f);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.pDataCotacaoInicio);
		parametros.put("data_fim", this.pDataCotacaoFim);
		
		if (this.itemPeca != null) {
			parametros.put("P_SQL", "AND xm.item_peca.descricao = '" + this.itemPeca.getDescricao() + "'");
		}
		
		if (this.fornecedor != null) {
			parametros.put("fornecedor", " AND xm.pessoa.nome = '"+this.fornecedor.getFornecedor().getNome() +"'");
		}
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioDeEstoque.jasper", 
				this.response, parametros, "Relatorio Estoque.pdf");
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

	public List<ItemPeca> getItemPecas() {
		return itemPecas;
	}

	public void setItemPecas(List<ItemPeca> itemPecas) {
		this.itemPecas = itemPecas;
	}

	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	
	
	
}
