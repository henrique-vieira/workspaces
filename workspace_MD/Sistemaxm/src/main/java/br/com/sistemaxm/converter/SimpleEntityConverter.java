package br.com.sistemaxm.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sistemaxm.entidades.Cliente;

@FacesConverter(value="simpleEntityConverter")
public class SimpleEntityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
			}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value != null && !"".equals(value)) {
			Cliente cliente = new Cliente();
			cliente = (Cliente) value;
			
			this.addAttribute(component, cliente);
			
			Long codigo = cliente.getCodigo();
			
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}
		return (String) value;
	}

	 protected void addAttribute(UIComponent component, Cliente o) {  
	        String key = o.getCodigo().toString(); // codigo da empresa como chave neste caso  
	        this.getAttributesFrom(component).put(key, o);  
	    }  
	  
	    protected Map<String, Object> getAttributesFrom(UIComponent component) {  
	        return component.getAttributes();  
	    }  
}
