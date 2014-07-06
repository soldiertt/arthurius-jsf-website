package be.arthurius.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import be.arthurius.core.model.OrderLine;
import be.arthurius.core.model.User;
import be.arthurius.core.service.UserService;
import be.arthurius.core.utils.ArthuriusUtils;

@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7840522602143690896L;
	
	@EJB
	private UserService userService;
	
	private User activeUser;
	
	private User newUser;
	private String passwordConfirm;
	private String emailConfirm;
	private String emailSendTo;
	
	private String signinUserName;
	private String signinPassword;
	private List<OrderLine> shoppingCart;
	private Boolean editPassword = false;
	
	public User getActiveUser() {
		return activeUser;
	}

	public String getSigninUserName() {
		return signinUserName;
	}

	public void setSigninUserName(String signinUserName) {
		this.signinUserName = signinUserName;
	}

	public String getSigninPassword() {
		return signinPassword;
	}

	public void setSigninPassword(String signinPassword) {
		this.signinPassword = signinPassword;
	}

	public List<OrderLine> getShoppingCart() {
		if (shoppingCart == null || activeUser == null) {
			shoppingCart = new ArrayList<OrderLine>();
		}
		return shoppingCart;
	}

	public User getNewUser() {
		if (newUser == null) {
			newUser = new User(true);
		}
		return newUser;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getEmailConfirm() {
		return emailConfirm;
	}
	
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}

	public String getEmailSendTo() {
		return emailSendTo;
	}

	public Boolean getEditPassword() {
		return editPassword;
	}

	public void setEditPassword(Boolean editPassword) {
		this.editPassword = editPassword;
	}

	public String getSessionId() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getSession().getId();
	}
	
	public String actionSubmitUser() {
		try {
			userService.saveUser(newUser);
			ArthuriusUtils.postMailUserRegistration("confirmemail", newUser);
			newUser = null;
			passwordConfirm = null;
			emailSendTo = emailConfirm;
			emailConfirm = null;
		} catch (MessagingException e) {
			//@TODO
			e.printStackTrace();
		}
		return "userreg_confirm";
	}
	
	public void listenerChangePassword(ValueChangeEvent e) {
		if (editPassword) {
			editPassword = false;
		} else {
			editPassword = true;
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public String actionSignIn() {
		this.activeUser = userService.getUserByUserName(this.signinUserName);
		return "homepage";
	}
	
	public String actionSignOut() {
		this.activeUser = null;
		return "homepage";
	}
	
}
