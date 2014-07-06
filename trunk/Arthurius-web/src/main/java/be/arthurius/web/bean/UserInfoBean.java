package be.arthurius.web.bean;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.crypto.CryptoUtil;

import be.arthurius.core.model.OrderLine;
import be.arthurius.core.model.User;
import be.arthurius.core.service.UserService;
import be.arthurius.core.utils.ArthuriusUtils;
import be.arthurius.web.util.LangUtil;

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
	private User loginUser;
	
	private User newUser;
	private String passwordConfirm;
	private String emailConfirm;
	private String emailSendTo;
	
	private String signinUserName;
	private String signinPassword;
	private String requestedUrl = null;
	private List<OrderLine> shoppingCart;
	private Boolean editPassword = false;
	
	public User getActiveUser() {
		if (activeUser == null) { 
			Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			if (principal != null) {
				activeUser = userService.getUserByUserName(principal.getName());
			}
		}
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
			String hashPasswd = CryptoUtil.createPasswordHash("SHA-256", "base64", null, newUser.getUserName(), newUser.getPassword());
			newUser.setPassword(hashPasswd);
			userService.saveUser(newUser);
			ArthuriusUtils.postMailUserRegistration("confirmemail", newUser);
			newUser = null;
			passwordConfirm = null;
			emailSendTo = emailConfirm;
			emailConfirm = null;
		} catch (MessagingException e) {
			//TODO
			e.printStackTrace();
		}
		return "/pages/userreg_confirm";
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
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			this.loginUser = userService.getUserByUserName(this.signinUserName);
			if (loginUser != null && loginUser.getValidated()) {
				request.login(this.signinUserName, this.signinPassword);
				this.activeUser = loginUser;
				if (requestedUrl == null) {
					requestedUrl = "/pages/homepage.jsf";
				}
				int startIndex = requestedUrl.indexOf("/pages");
				return requestedUrl.substring(startIndex);
			} else {
				context.addMessage(null, LangUtil.getFacesMessage(context, "error.user.invaliduserorpassword"));
				return "";
			}
		} catch (ServletException e) {  
			context.addMessage(null, LangUtil.getFacesMessage(context, "error.user.invaliduserorpassword"));
			return "";
		}
	}
	
	public String getRequestedUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String forwardUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");
		//Uniquement si on arrive sur la page de login et que l'url est non null
		if (request.getRequestURL().indexOf("user_signin") != -1 && forwardUrl != null) {
			requestedUrl = forwardUrl;
		}
		return requestedUrl;
	}
	
	public void setRequestedUrl(String requestedUrl) {
		if (requestedUrl.equals("")) {
			this.requestedUrl = null;
		} else {
			this.requestedUrl = requestedUrl;
		}
	}

	public String actionSignOut() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.activeUser = null;
		this.requestedUrl = null;
		return "/pages/homepage";
	}
	
}
