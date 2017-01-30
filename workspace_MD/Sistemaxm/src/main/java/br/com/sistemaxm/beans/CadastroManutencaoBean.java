package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import br.com.sistemaxm.business.CadastroItemPeca;
import br.com.sistemaxm.business.CadastroManutencao;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Manutencao;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ManutencaoRepository;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@ViewScoped
public class CadastroManutencaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ItemPeca itemPeca;
	private Carro carro;
	private Servico servico;
	private Manutencao manutencao;
	private List<Manutencao> listaManutencao;
	private List<Carro> carros;
	private List<ItemPeca> itemPecas;
	private List<Servico> servicos;
	private boolean showModal;

	public CadastroManutencaoBean() {
		this.itemPeca = new ItemPeca(new Long(0), new String());
		this.carro = new Carro(new Long(0), new String(), new String());
		this.servico = new Servico(new Long(0), new String());
		this.manutencao = new Manutencao();
	}
	
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				if (c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())
						|| c.getModelo().toLowerCase()
								.startsWith(busca.toLowerCase())) {
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
			ItemPecaRepository itemPecaRepo = new ItemPecaRepository(manager);
			this.itemPecas = itemPecaRepo.itensCombo();
			for (ItemPeca i : itemPecas) {
				if (i.getDescricao().toLowerCase()
						.startsWith(busca.toLowerCase())) {
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
			ServicoRepository servicoRepo = new ServicoRepository(manager);
			this.servicos = servicoRepo.todos();
			for (Servico s : servicos) {
				if (s.getDescricao().toLowerCase()
						.startsWith(busca.toLowerCase())) {
					auxLista.add(s);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}

	public void listaManutencaoBean() {
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			ManutencaoRepository manutencaoRepo = new ManutencaoRepository(
					manager);
			this.listaManutencao = manutencaoRepo.todos();
		} finally {
			manager.close();
		}
	}
	// atualiza a quantidade maxima de item no spinner e verifica quantidade minima no estoque.
	public void onItemSelectQtde(SelectEvent event) {
		this.itemPeca = (ItemPeca) event.getObject();
		this.itemPeca.getQtde();
		
		if (this.itemPeca.getQtde() <= 2) {
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Quatidade minima no estoque!", event.getObject().toString()));
			this.showModal = true;
		}
	}
	
	// renderiza o campo km_troca de acordo com o veículo selecionado.
	public void onItemSelectKmTroca(SelectEvent event) {
		this.carro = (Carro) event.getObject();
		this.carro.getKm();
	}
	
	// atualiza a quantidade de itens no estoque.
	public void atualizaQtdeItemPeca(int qtde, Long codigoItem, boolean devolucao) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			trx.begin();
			CadastroItemPeca cadastro = new CadastroItemPeca(
					new ItemPecaRepository(manager));
			ItemPeca ip = manager.find(ItemPeca.class, codigoItem);
			// ip.setQtde(ip.getQtde()-1);
			if (!devolucao) {
				ip.setQtde(ip.getQtde()-qtde);
			}
			else {
				ip.setQtde(ip.getQtde()+qtde);
			}
			cadastro.editar(ip);
			trx.commit();
			ip = new ItemPeca();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Qtde do item atualizada no estoque!", null));
		} catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Qtde do item não atualizada.", null));
		} finally {
			manager.close();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.manutencao = (Manutencao) event.getObject();
		int qtdeResult = 0;
		Long codigoItem = new Long(0);
		boolean qtdeDevolucao = false;
		try {
			trx.begin();
			CadastroManutencao cadastro = new CadastroManutencao(
					new ManutencaoRepository(manager));

			this.manutencao
					.setCodigo(((Manutencao) manager.find(Manutencao.class,
							this.manutencao.getCodigo())).getCodigo());
			// busca informações do objeto com o estado antes da edição
			Manutencao m = new Manutencao();
			m = manager.find(Manutencao.class, this.manutencao.getCodigo());

			codigoItem = this.manutencao.getItemPeca().getCodigo();
			if (m.getQtde() > this.manutencao.getQtde()) {
				qtdeDevolucao = true;
				qtdeResult = m.getQtde() - this.manutencao.getQtde();
				
			} else {
				qtdeDevolucao = false;
				qtdeResult = this.manutencao.getQtde() - m.getQtde();
			}

			cadastro.editar(manutencao);
			trx.commit();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Registro alterado com sucesso!", null));
			this.manutencao = new Manutencao();

		} catch (Exception e) {
			trx.rollback();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"registro não alterado!", null));
		} finally {
			manager.close();
			atualizaQtdeItemPeca(qtdeResult, codigoItem, qtdeDevolucao);
		}
	}

	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		int qtde = 0;
		Long codigoItem = new Long(0);
		boolean qtdeDevolucao = false;
		try {
			trx.begin();
			this.manutencao.setKmTroca(this.carro.getKm());
			this.manutencao.setCarro(this.carro);
			this.manutencao.setServico(this.servico);
			this.manutencao.setItemPeca(this.itemPeca);
			CadastroManutencao cadastro = new CadastroManutencao(
					new ManutencaoRepository(manager));

			if (this.manutencao.getItemPeca().getQtde() > 0) {
				cadastro.salvar(manutencao);
				qtde = this.manutencao.getQtde();
				codigoItem = manutencao.getItemPeca().getCodigo();
				trx.commit();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Manutenção cadastrada com sucesso!", null));
				this.manutencao = new Manutencao();
				this.carro = new Carro(new Long(0), new String(), new String());
				this.servico = new Servico(new Long(0), new String());
				this.itemPeca = new ItemPeca(new Long(0), new String());
			} else {
				trx.rollback();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Item não disponível. Verifique a quantidade.", null));
			}
		} catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar manutenção.",
					null));
		} finally {
			manager.close();
			atualizaQtdeItemPeca(qtde, codigoItem, qtdeDevolucao);
		}
	}

	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Manutencao> getListaManutencao() {
		return listaManutencao;
	}

	public void setListaManutencao(List<Manutencao> listaManutencao) {
		this.listaManutencao = listaManutencao;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public List<ItemPeca> getItemPecas() {
		return itemPecas;
	}

	public void setItemPecas(List<ItemPeca> itemPecas) {
		this.itemPecas = itemPecas;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}


	public boolean isShowModal() {
		return showModal;
	}


	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}

	
}
