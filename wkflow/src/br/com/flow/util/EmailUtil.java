package br.com.flow.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
	public void enviarEmail(String de, String para, String assunto, String mensagem) throws Exception {
		/*
		 HtmlEmail he = new HtmlEmail();
		 File img = new File("my/image.gif");
		 PNGDataSource png = new PNGDataSource(decodedPNGOutputStream); // a custom class
		 StringBuffer msg = new StringBuffer();
		 msg.append("<html><body>");
		 msg.append("<img src=cid:").append(he.embed(img)).append(">");
		 msg.append("<img src=cid:").append(he.embed(png)).append(">");
		 msg.append("</body></html>");
		 he.setHtmlMsg(msg.toString());
		 // code to set the other email fields (not shown)
		 
		 */
		try {
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
			Session session = (Session) envContext.lookup("mail/Session");
			SimpleEmail email = new SimpleEmail();
			email.setMailSession(session);
			if (de != null) {
				email.setFrom(de);
			} else {
				Properties p = session.getProperties();
				email.setFrom(p.getProperty("mail.smtp.user"));
			}
			email.addTo(para);
			email.setSubject(assunto);
			email.setMsg(mensagem);
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException | NamingException e) {
			throw e;
		}
	}
}
