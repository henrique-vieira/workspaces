package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroCarros;
import br.com.sistemaxm.business.CadastroLocacao;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.ContratoLocacao;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class EditViewContratoLocacao implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private List<ContratoLocacao> contratos;	
	private ContratoLocacao contLoc = new ContratoLocacao();
	ContratoLocacaoRepository contRepo;
	
	public void init() {
		this.contratos = contRepo.todos();
	}

	public List<ContratoLocacao> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoLocacao> contratos) {
		this.contratos = contratos;
	}

	public ContratoLocacao getContLoc() {
		return contLoc;
	}

	public void setContLoc(ContratoLocacao contLoc) {
		this.contLoc = contLoc;
	}
	
	public void atualizarKmVeiculo(Carro carroParam) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		Carro carro = new Carro();
		
		try {
			trx.begin();
			CadastroCarros cadastro = new CadastroCarros(new CarroRepository(manager));
			carro = carroParam;
			cadastro.editar(carro);
			carro = new Carro();
			trx.commit();			
		}
		catch(Exception e) {
			trx.rollback();
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.contLoc = (ContratoLocacao) event.getObject();
		try {
			trx.begin();
			CadastroLocacao cadastro = new CadastroLocacao(new ContratoLocacaoRepository(manager));
			this.contLoc.setCodigo(((ContratoLocacao)manager.find(ContratoLocacao.class, contLoc.getCodigo())).getCodigo());
			/*contLoc.setKmChegada(contLoc.getKmChegada());
			contLoc.setObs(contLoc.getObs());*/
			
			if (contLoc.getKmChegada() != null && !"".equals(contLoc.getKmChegada())) {
				Carro carro = contLoc.getCarro();
				Long kmChegada = contLoc.getKmChegada();
				carro.setKm(kmChegada);
				atualizarKmVeiculo(carro);
			}
								
			cadastro.editar(this.contLoc);
			this.contLoc = new ContratoLocacao();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!",null));
		}
		
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado!",null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
