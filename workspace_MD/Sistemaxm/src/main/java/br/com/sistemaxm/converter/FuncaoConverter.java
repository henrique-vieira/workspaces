package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Funcao;
import br.com.sistemaxm.repository.FuncaoRepository;
import br.com.sistemaxm.repository.JpaUtil;



@FacesConverter(forClass = Funcao.class,value="funcaoConverter")
public class FuncaoConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Funcao retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			if (value != null && !"".equals(value)) {
				FuncaoRepository funcaoRepo = new FuncaoRepository(manager);
				retorno = funcaoRepo.porId(new Long(value));
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
			return ((Funcao)value).getCodigo().toString();
		}
		return null;
	}

}
