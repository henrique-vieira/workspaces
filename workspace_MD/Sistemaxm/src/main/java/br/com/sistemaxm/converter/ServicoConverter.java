package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.AbstractRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ServicoRepository;

@FacesConverter(value="servicoConverter")
public class ServicoConverter implements Converter {

	public ServicoConverter() {
		
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Servico retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			if (value != null && !"".equals(value)) {
				ServicoRepository servicoRepo = new ServicoRepository(manager);
				retorno = servicoRepo.porId(new Long(value));
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
			return ((Servico) value).getCodigo().toString();
		}
		return null;
	}

}
