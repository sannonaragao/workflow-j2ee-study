package br.com.flow.web.mb;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("ApplicationMB")
@ApplicationScoped
public class ApplicationMB  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2382345998058899407L;
	
	private static final String paginatorTemplate = "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
	private static final String rowsPerPageTemplate="5,10,15";
	
	
	public String getPaginatortemplate() {
		return paginatorTemplate;
	}
	public String getRowsperpagetemplate() {
		return rowsPerPageTemplate;
	}

}
