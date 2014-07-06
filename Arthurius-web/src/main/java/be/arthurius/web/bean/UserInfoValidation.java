package be.arthurius.web.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import be.arthurius.core.model.User;
import be.arthurius.core.service.UserService;
import be.arthurius.web.util.LangUtil;

@ManagedBean
@RequestScoped
public class UserInfoValidation implements Serializable {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 7929015448655669414L;

	@EJB
	private UserService userService;
	
	public void validateUserName(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		if (value != null && !value.equals("")) {
			String userName = (String) value;
			/***** check if userName is not already used in db *********/
			User testUser = userService.getUserByUserName(userName);
			if (testUser != null) {
				FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.usernamealreadyexists");
				throw new ValidatorException(msg);
			}
		}
	}
	
	public void validatePasswordConfirm(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		if (value != null && !value.equals("")) {
			String passwordConfirm = (String) value;
			String passwordId = (String) toValidate.getAttributes().get("passwordId");
	        UIInput passwordInput = (UIInput) context.getViewRoot().findComponent(passwordId);
	        String password = (String) passwordInput.getValue();
			if (!passwordConfirm.equals(password)) {
				FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.passwordconfirm");
				throw new ValidatorException(msg);
			}
		}
	}
	
	public void validateOnlyLetters(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		if (value != null && !value.equals("")) {
			String field = (String) value;
			for (int i=0; i < field.length(); i++) {
				if (Character.isDigit(field.charAt(i))) {
					FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.cannotcontainsdigit");
					throw new ValidatorException(msg);
				}
			}
		}
	}
	
	public void validateEmailConfirm(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		if (value != null && !value.equals("")) {
			String emailConfirm = (String) value;
			String emailId = (String) toValidate.getAttributes().get("emailId");
	        UIInput emailInput = (UIInput) context.getViewRoot().findComponent(emailId);
	        String email = (String) emailInput.getValue();
	        if (email != null) {
				if (!email.equals(emailConfirm)) {
					FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.emailconfirm");
					throw new ValidatorException(msg);
				}
				/***** check if email is not already used in db *********/
				User testUser = userService.getUserByEmail(email);
				if (testUser != null) {
					FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.emailalreadyexists");
					throw new ValidatorException(msg);
				}
	        }
		}
	}
	
	/**
	 * TODO REMOVE - NOT MORE USED (old login form)
	 * @param context
	 * @param toValidate
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateSignIn(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		if (value != null && !value.equals("")) {
			String signPassword = (String) value;
			String signUserNameId = (String) toValidate.getAttributes().get("signinUserNameId");
	        UIInput signUserNameInput = (UIInput) context.getViewRoot().findComponent(signUserNameId);
	        String signUserName = (String) signUserNameInput.getValue();
	        if (signUserName != null) {
				User signUser = userService.getUserByUserName(signUserName);
				if (signUser == null || !signUser.getValidated()) {
					FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.invaliduserorpassword");
					throw new ValidatorException(msg);
				} else if (!signUser.getPassword().equals(signPassword)) {
					FacesMessage msg = LangUtil.getFacesMessage(context, "error.user.invaliduserorpassword");
					throw new ValidatorException(msg);
				}
	        }
		}
	}
}
