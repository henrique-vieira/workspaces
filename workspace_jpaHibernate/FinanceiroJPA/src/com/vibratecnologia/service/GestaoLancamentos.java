package com.vibratecnologia.service;

import com.vibratecnologia.model.Lancamento;
import com.vibratecnologia.repository.Lancamentos;

public class GestaoLancamentos {
	
	private Lancamentos lancamentos;
	
	public GestaoLancamentos(Lancamentos lancamentos){
		this.lancamentos = lancamentos;
	}
	
	public void salvar(Lancamento lancamento) throws RegraNegocioException{
		
		if(existeLancamentoSemelhante(lancamento)){
			throw new RegraNegocioException("existing_entry");//N�o est� funcionando, coloquei a mensagem direto no managedBean
		}
		
		this.lancamentos.guardar(lancamento);
	}
	
	private boolean existeLancamentoSemelhante(Lancamento lancamento){
		Lancamento lancamentoSemelhante = this.lancamentos.comDadosIguais(lancamento);
		
		return lancamentoSemelhante != null && !lancamentoSemelhante.equals(lancamento);
			
		
	}
	
	public void excluir(Lancamento lancamento) throws RegraNegocioException {
		if(lancamento.isPago()){
			throw new RegraNegocioException("Este lan�amento j� foi pago e n�o pode ser exclu�do.");//N�o est� funcionando, coloquei a mensagem direto no managedBean
		}
		
		this.lancamentos.remover(lancamento);
	}
}
