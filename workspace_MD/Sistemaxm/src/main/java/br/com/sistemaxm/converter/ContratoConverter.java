package br.com.sistemaxm.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;


import br.com.sistemaxm.entidades.Contrato;
import br.com.sistemaxm.repository.ContratoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@FacesConverter(forClass=Contrato.class,value="contratoConverter")
public class ContratoConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Contrato retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			if (value != null && !"".equals(value)) {
				ContratoRepository contratoRepo = new ContratoRepository(manager);
				retorno = contratoRepo.porId(new Long(value));
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
			return ((Contrato) value).getCodigo().toString();
		}
		return null;
	}

}
