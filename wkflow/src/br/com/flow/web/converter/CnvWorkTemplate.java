package br.com.flow.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.flow.model.dto.WorkTemplateDTO;


@FacesConverter(value="cnvWorkTemplate")
public class CnvWorkTemplate implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (WorkTemplateDTO) uiComponent.getAttributes().get(value);
		}
		
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof WorkTemplateDTO) {
			WorkTemplateDTO worktemplate = (WorkTemplateDTO) value;
			if (worktemplate != null && worktemplate instanceof WorkTemplateDTO ) {
				if( worktemplate.getDescription() == null || worktemplate.getDescription().isEmpty() ){
					uiComponent.getAttributes().put("Nova Etapa", worktemplate);
					return "Nova Etapa";					
				}
				uiComponent.getAttributes().put(worktemplate.getDescription().toString(), worktemplate);
				return worktemplate.getDescription().toString();
			}
		}
		return value.toString();
	}
}
