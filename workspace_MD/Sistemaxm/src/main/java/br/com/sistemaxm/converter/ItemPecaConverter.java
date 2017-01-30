package br.com.sistemaxm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;

@FacesConverter(forClass=ItemPeca.class, value="itemPecaConverter")
public class ItemPecaConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		ItemPeca retorno = null;
		EntityManager manager = JpaUtil.getEntityManager();
			try {
				if (value != null && !"".equals(value)) {
					ItemPecaRepository itemPecaRepo = new ItemPecaRepository(manager);
					retorno = itemPecaRepo.porId(new Long(value));
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
				return ((ItemPeca) value).getCodigo().toString();
			}
			return null;
	}

}
