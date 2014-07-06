package be.arthurius.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.sun.faces.util.MessageFactory;

@FacesValidator(value="emailValidator")
public class EmailValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		if (value != null) {
			if (!(value instanceof String)) {
				FacesMessage msg = MessageFactory.getMessage("error.user.valuetype");
				throw new IllegalArgumentException(msg.getDetail());
			}
			String email = (String) value;
			
			try {
				new InternetAddress(email);
			    if (!hasNameAndDomain(email)) {
			    	FacesMessage msg = MessageFactory.getMessage("error.user.email");
					throw new ValidatorException(msg);
			    }
			} catch (AddressException ex) {
			    FacesMessage msg = MessageFactory.getMessage("error.user.email");
				throw new ValidatorException(msg);
			}
		}
	}

	private static boolean hasNameAndDomain(String aEmailAddress){
	    String[] tokens = aEmailAddress.split("@");
	    return tokens.length == 2 && tokens[0].trim().length() > 0 && tokens[1].trim().length() > 0 && tokens[1].contains(".");
	}
}
