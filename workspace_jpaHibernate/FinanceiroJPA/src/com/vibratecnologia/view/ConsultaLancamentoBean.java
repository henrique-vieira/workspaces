package com.vibratecnologia.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import com.vibratecnologia.model.Lancamento;
import com.vibratecnologia.repository.Lancamentos;
import com.vibratecnologia.service.GestaoLancamentos;
import com.vibratecnologia.service.RegraNegocioException;
import com.vibratecnologia.util.FacesUtil;
import com.vibratecnologia.util.Repositorios;

@ManagedBean
public class ConsultaLancamentoBean implements Serializable {

	private Repositorios repositorios = new Repositorios();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private Lancamento lancamentoSelecionado;
	
	@PostConstruct
	public void inicializar() {
		
		Lancamentos lancamentos = this.repositorios.getLancamentos();
		this.lancamentos = lancamentos.todos();
		
	}
	
	public void excluir(){
		
		GestaoLancamentos gestaoLancamentos = new GestaoLancamentos(this.repositorios.getLancamentos());
		try {
			gestaoLancamentos.excluir(lancamentoSelecionado);
			
			this.inicializar();
			
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lançamento excluído com sucesso.");
			
		} catch (RegraNegocioException e) {
			
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Este lançamento já foi pago e não pode ser excluído.");
		}
				
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public Lancamento getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}
	
	
	
}
