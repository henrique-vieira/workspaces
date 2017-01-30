package br.com.sistemaxm.beans;

import java.io.Serializable;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroFornecedor;
import br.com.sistemaxm.business.CadastroFuncionario;
import br.com.sistemaxm.entidades.Aso;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Endereco;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.Funcao;
import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.enums.Sexo;
import br.com.sistemaxm.enums.StatusFuncionario;
import br.com.sistemaxm.enums.TipoReceita;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.FuncaoRepository;
import br.com.sistemaxm.repository.FuncionarioRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private PessoaFisica pessoaFisica;	
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<PessoaFisica> pessoasFisica;
	private List<Funcao> funcoes;	
	private Funcao funcao;
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private Endereco endereco;
	private Aso aso;
	
	// Bean que possui o método de busca do CEP
	@ManagedProperty(value = "#{testeCepBean}")
	private TesteCepBean testeCepBean;

	public CadastroFuncionarioBean() {
		this.pessoaFisica = new PessoaFisica();		
		this.funcionario = new Funcionario();
		this.funcao = new Funcao(new Long(0), new String());
		this.endereco = new Endereco();
		this.aso = new Aso();
		this.pessoa = new Pessoa();
		this.cliente = new Cliente(new Long(0), new PessoaJuridica());
	}
	// Popula listas de funções
	@PostConstruct
	public void init() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FuncaoRepository funcaoRepo = new FuncaoRepository(manager);
			this.funcoes = funcaoRepo.listaFuncao();
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
	
	public List<SelectItem> getStatusFuncionario() { 
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (StatusFuncionario statusEnum : StatusFuncionario.values()) {
			auxLista.add(new SelectItem(statusEnum, statusEnum.getValue()));
		}
		return auxLista;
	}
	
	public void consultaFuncionarios() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FuncionarioRepository funcionarioRepo = new FuncionarioRepository(manager);
			this.funcionarios = funcionarioRepo.todosFuncionarios();
			this.pessoasFisica = funcionarioRepo.pessoasFisica();
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}
	
	// Retorna a data periodica de realização do ASO
	public Date retornaDataPeriodicaAso() {
		Calendar c = Calendar.getInstance();
		c.setTime(this.aso.getDataExameAdmissional());
		c.add(c.MONTH, +12);
		this.aso.setDataExamePeriodico(c.getTime());
		return aso.getDataExamePeriodico();
		
	}
	// Radiobutton de sexo
	public List<SelectItem> getTipoItemListSexo() {
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (Sexo sexo : Sexo.values()) {
			auxLista.add(new SelectItem(sexo, sexo.getValue()));
		}
		return auxLista;
		
	}
	// Método que busca o CEP e popula os campos de endereço
	public void buscaCep(AjaxBehaviorEvent event) {
		this.testeCepBean.getEndereco().setCep(this.endereco.getCep());
		this.testeCepBean.buscaEndereco(event);
		this.endereco.setLogradouro(this.testeCepBean.getEndereco().getLogradouro());
		this.endereco.setBairro(this.testeCepBean.getEndereco().getBairro());
		this.endereco.setCidade(this.testeCepBean.getEndereco().getCidade());
		this.endereco.setUf(this.testeCepBean.getEndereco().getUf());
	}	
	
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
			
	public TesteCepBean getTesteCepBean() {
		return testeCepBean;
	}

	public void setTesteCepBean(TesteCepBean testeCepBean) {
		this.testeCepBean = testeCepBean;
	}
		
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}		

	public Aso getAso() {
		return aso;
	}

	public void setAso(Aso aso) {
		this.aso = aso;
	}	
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public List<PessoaFisica> getPessoasFisica() {
		return pessoasFisica;
	}
	
	public void setPessoasFisica(List<PessoaFisica> pessoasFisica) {
		this.pessoasFisica = pessoasFisica;
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.funcionario = (Funcionario) event.getObject();
		
		try {
			trx.begin();
			CadastroFuncionario cadastro = new CadastroFuncionario(new FuncionarioRepository(manager));			
			this.funcionario.setCodigo(((Funcionario)manager.find(Funcionario.class, this.funcionario.getCodigo())).getCodigo());
			cadastro.editar(funcionario);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado com sucesso!", null));
			this.funcionario = new Funcionario();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não alterado!", null));
		}
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		
		try {
			trx.begin();
			this.aso.setDataExameAdmissional(this.funcionario.getDataAdmissao());
			System.out.println(retornaDataPeriodicaAso());
			this.funcionario.setFuncao(this.funcao);
			this.pessoaFisica.setEndereco(this.endereco);
			this.funcionario.setPessoa(this.pessoaFisica);			
			this.funcionario.setAso(this.aso);
			this.funcionario.setCliente(this.cliente);
			CadastroFuncionario cadastro = new CadastroFuncionario(new FuncionarioRepository(manager));
			cadastro.salvar(funcionario);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Funcionário cadastrado com sucesso !",null));
			this.funcao = new Funcao();
			this.funcionario = new Funcionario();
			this.pessoaFisica = new PessoaFisica();
			this.cliente = new Cliente();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar Funcionário.",null));
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
			CadastroFuncionario cadastro = new CadastroFuncionario(new FuncionarioRepository(manager));
			this.funcionario = manager.find(Funcionario.class, this.funcionario.getCodigo());			
			cadastro.excluir(funcionario);		
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso", null));
			this.funcionario = new Funcionario();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluido!", null));
		}
		finally {
			manager.close();
		}
	}

		
	
}
