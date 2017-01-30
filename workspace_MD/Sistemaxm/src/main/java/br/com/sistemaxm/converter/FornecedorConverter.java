package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@FacesConverter(forClass=Fornecedor.class, value="fornecedorConverter")
public class FornecedorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Fornecedor retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			if (value != null && !"".equals(value)) {
				FornecedorRepository fornecedorRepo = new FornecedorRepository(manager);
				retorno = fornecedorRepo.porId(new Long(value));
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
		if (value != null) {
			return ((Fornecedor) value).getCodigo().toString();
		}
		return null;
	}

}
