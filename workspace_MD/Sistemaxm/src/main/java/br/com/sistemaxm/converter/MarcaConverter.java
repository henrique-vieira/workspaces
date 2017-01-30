package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;

@FacesConverter(forClass=Marca.class, value="marcaConverter")
public class MarcaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		// TODO Auto-generated method stub
		Marca retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			if (value != null && !"".equals(value)) {
				MarcaRepository marcaRepo = new MarcaRepository(manager);
				retorno = marcaRepo.porId(new Long(value));
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
			return ((Marca) value).getCodigo().toString();
		}
		return null;
	}

}
