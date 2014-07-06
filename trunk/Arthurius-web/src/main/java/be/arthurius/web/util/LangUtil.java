package be.arthurius.web.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LangUtil {
	
	public static String getMessage(FacesContext ctx, String messageKey) {
		ResourceBundle bundle = ResourceBundle.getBundle("be.arthurius.web.messages.Messages", ctx.getViewRoot().getLocale());
		return bundle.getString(messageKey);
	}
	
	public static FacesMessage getFacesMessage(FacesContext ctx, String messageKey) {
		return new FacesMessage(LangUtil.getMessage(ctx, messageKey));
	}
}
