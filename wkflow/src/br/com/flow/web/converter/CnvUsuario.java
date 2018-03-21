package br.com.flow.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.flow.model.dto.UsuarioDTO;


@FacesConverter(value="cnvUsuario")
public class CnvUsuario implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (UsuarioDTO) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof UsuarioDTO) {
			UsuarioDTO usuario = (UsuarioDTO) value;
			if (usuario != null && usuario instanceof UsuarioDTO && usuario.getId() != null) {
				uiComponent.getAttributes().put(usuario.getNome().toString(), usuario);
				return usuario.getNome().toString();
			}
		}
		return "";
	}
}
