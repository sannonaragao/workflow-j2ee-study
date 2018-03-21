package br.com.flow.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.flow.model.dto.UserGroupDTO;;


@FacesConverter(value="cnvUserGroup")
public class CnvUserGroup implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (UserGroupDTO) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof UserGroupDTO) {
			UserGroupDTO usergroup = (UserGroupDTO) value;
			if (usergroup != null && usergroup instanceof UserGroupDTO && usergroup.getId() != null) {
				uiComponent.getAttributes().put(usergroup.getName().toString(), usergroup);
				return usergroup.getName().toString();
			}
		}
		return "";
	}
}
