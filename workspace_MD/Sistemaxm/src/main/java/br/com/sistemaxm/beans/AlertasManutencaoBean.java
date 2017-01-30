package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import org.primefaces.event.SelectEvent;

import br.com.sistemaxm.entidades.Manutencao;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ManutencaoRepository;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@ViewScoped
public class AlertasManutencaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Servico servico;
	private List<Servico> servicos;
	private List<Manutencao> manutencoes;
	private List<Object[]> listaAlertasManutencao;

	public AlertasManutencaoBean() {
		this.servico = new Servico(new Long(0), new String());

	}

	public List<Servico> autoCompleteServico(String s) {
		List<Servico> auxLista = new ArrayList<Servico>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			ServicoRepository servicoRepo = new ServicoRepository(manager);
			this.servicos = servicoRepo.todos();

			for (Servico serv : this.servicos) {
				if (serv.getDescricao().toLowerCase()
						.startsWith(s.toLowerCase())) {
					auxLista.add(serv);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}
	
	public void onItemSelect(SelectEvent event) {
		this.servico = (Servico) event.getObject();
		listaAlertaManutencaoDt(this.servico.getDescricao());		
	}

	public void listaAlertaManutencaoDt(String servico) {
		EntityManager manager = JpaUtil.getEntityManager();
		//descricao = this.servico.getDescricao();

		try {
			ManutencaoRepository manutencaoRepo = new ManutencaoRepository(
					manager);
			this.listaAlertasManutencao = manutencaoRepo
				.listaAlertaManutencoes(servico);
			
		} catch (Exception e) {
			e.getMessage();
		} finally {
			manager.close();
		}

	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Manutencao> getManutencoes() {
		return manutencoes;
	}

	public void setManutencoes(List<Manutencao> manutencoes) {
		this.manutencoes = manutencoes;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Object[]> getListaAlertasManutencao() {
		return listaAlertasManutencao;
	}

	public void setListaAlertasManutencao(List<Object[]> listaAlertasManutencao) {
		this.listaAlertasManutencao = listaAlertasManutencao;
	}

}
