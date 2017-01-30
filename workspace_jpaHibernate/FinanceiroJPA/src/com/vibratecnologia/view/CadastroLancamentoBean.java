package com.vibratecnologia.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.vibratecnologia.model.Lancamento;
import com.vibratecnologia.model.Pessoa;
import com.vibratecnologia.model.TipoLancamento;
import com.vibratecnologia.repository.Lancamentos;
import com.vibratecnologia.repository.Pessoas;
import com.vibratecnologia.repository.infra.PessoasHibernate;
import com.vibratecnologia.service.GestaoLancamentos;
import com.vibratecnologia.service.RegraNegocioException;
import com.vibratecnologia.util.FacesUtil;
import com.vibratecnologia.util.HibernateUtil;
import com.vibratecnologia.util.Repositorios;

@ManagedBean
@ViewScoped
public class CadastroLancamentoBean implements Serializable {

	private Repositorios repositorios = new Repositorios();
	private List<Pessoa> listPessoas = new ArrayList<Pessoa>();
	private Lancamento lancamento = new Lancamento();

	@PostConstruct
	public void init() {
				
		Pessoas pessoas = this.repositorios.getPessoas();//Instancia um novo repositório de pessoas
		this.listPessoas = pessoas.todas();
		
		
	}
	
	public void lancamentoPagoModificado(ValueChangeEvent event) {
		this.lancamento.setPago((Boolean) event.getNewValue());
		this.lancamento.setDataPagamento(null);
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void salvar() {
		
		GestaoLancamentos gestaoLancamentos = new GestaoLancamentos(this.repositorios.getLancamentos());
		try {
			gestaoLancamentos.salvar(lancamento);
			
			this.lancamento = new Lancamento();
			
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, FacesUtil.getMensagemI18n("entry_saved"));
			
		} catch (RegraNegocioException e) {
			
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, FacesUtil.getMensagemI18n(e.getMessage()));
		}
		
	}
	
	public boolean isEditando(){
		return this.lancamento.getCodigo() != null;//Se o código do lançamento não for nulo, é porque estamos editando um lançamento
	}
	
	public TipoLancamento[] getTiposLancamentos() {
		return TipoLancamento.values();
	}

	public Lancamento getLancamento() {
		
		return lancamento;
	}
	
	public void setLancamento(Lancamento lancamento) throws CloneNotSupportedException{
		this.lancamento = lancamento;
		if(this.lancamento == null){
			this.lancamento = new Lancamento();
		}else{
			this.lancamento = (Lancamento) lancamento.clone();
		}
	}

	public List<Pessoa> getPessoas() {
		return listPessoas;
	}
	
}