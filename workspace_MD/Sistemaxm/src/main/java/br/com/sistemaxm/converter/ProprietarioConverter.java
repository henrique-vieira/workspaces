package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;

@FacesConverter(forClass=Proprietario.class, value="proprietarioConverter")
public class ProprietarioConverter implements Converter {



	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Proprietario retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			if (value != null && !"".equals(value)) {
				ProprietarioRepository proprietarioRepo = new ProprietarioRepository(manager);
				retorno = proprietarioRepo.porId(new Long(value));
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
			return ((Proprietario) value).getCodigo().toString();
		}
		return null;
	}

}
