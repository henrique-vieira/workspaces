package br.com.sistemaxm.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.JpaUtil;

@FacesConverter(forClass=Carro.class,value="carroConverter")
public class CarroConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Carro retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			if (value != null && !"".equals(value)) {
				CarroRepository carroRepo = new CarroRepository(manager);
				retorno = carroRepo.porId(new Long(value));
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
			return ((Carro) value).getId().toString();
		}
		return null;
	}

}
