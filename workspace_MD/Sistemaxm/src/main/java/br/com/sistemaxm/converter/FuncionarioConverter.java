package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;






import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FuncionarioRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.PessoaRepository;

@FacesConverter(forClass=Cliente.class, value="funcionarioConverter")
public class FuncionarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		// TODO Auto-generated method stub
		Funcionario retorno = null;		
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			if (value != null && !"".equals(value)) {
				FuncionarioRepository funcionarioRepo = new FuncionarioRepository(manager);
				retorno = funcionarioRepo.porId(new Long(value));
				
			}
			return retorno;
		}
		
		finally {
			manager.close();
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		// TODO Auto-generated method stub
		if (value != null) {
			return ((Funcionario) value).getCodigo().toString();
		}
		return null;
	}

}
