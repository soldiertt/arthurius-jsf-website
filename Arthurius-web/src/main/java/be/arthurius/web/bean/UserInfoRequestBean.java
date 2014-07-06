package be.arthurius.web.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import be.arthurius.core.model.IPNNotification;
import be.arthurius.core.model.OrderLine;
import be.arthurius.core.model.Product;
import be.arthurius.core.model.User;
import be.arthurius.core.service.ProductService;
import be.arthurius.core.service.UserService;
import be.arthurius.web.util.LangUtil;

@ManagedBean
@RequestScoped
public class UserInfoRequestBean implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -1098224898555603229L;

	@EJB
	private UserService userService;
	
	@EJB
	private ProductService productService;
	
	@ManagedProperty(value="#{userInfoBean}")
	private UserInfoBean userInfoBean;
	
	private String confirmEmailMessage;
	private Boolean confirmEmailOK;
	private String msgInfo;
	private IPNNotification ipnNotif;
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	
	public String getConfirmEmailMessage() {
		confirmEmailOK = false;
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();
		String email = params.get("email");
		String chuck = params.get("chuck");
		if (email != null && chuck != null) {
			User user = userService.getUserByEmail(email);
			if (!user.getValidated() && user.getChuckCode().equals(chuck)) {
				user.setValidated(true);
				user.setChuckCode(null);
				userService.saveUser(user);
				confirmEmailMessage = LangUtil.getMessage(ctx, "page.userreg_confirmemail.message");
				confirmEmailOK = true;
			}
		}
		if (!confirmEmailOK) {
			confirmEmailMessage = LangUtil.getMessage(ctx, "page.userreg_confirmemail.errormessage");
		}
		return confirmEmailMessage;
	}
	
	public Boolean getConfirmEmailOK() {
		return confirmEmailOK;
	}

	public String getShopcartNotificationProcess() throws RuntimeException {
		// read post from PayPal system and add 'cmd'
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Enumeration<String> en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		while (en.hasMoreElements()) {
			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			try {
				str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "ISO-8859-1" ) ;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		/* Extract form data */
		URL u;
		try {
			u = new URL("http://www.sandbox.paypal.com/cgi-bin/webscr");
			
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" ) ;
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(str);
			pw.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String ipnres = in.readLine();
			in.close();
			
			if (ipnres.equals("VERIFIED" )) {
				if (!CheckPayPal(request)) {
					//throw new RuntimeException("IPNNotification failed to validate !");
					return "NOK";
				}
				userService.paypalSaveNotification(ipnNotif);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	private boolean CheckPayPal(HttpServletRequest request) {
		ipnNotif = new IPNNotification();
		@SuppressWarnings("unchecked")
		List<String> sessionIds = (List<String>) ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute("sessionIds");
		try {
			for (Field field : ipnNotif.getClass().getDeclaredFields()) {
				if (request.getParameter(field.getName()) != null) {
					try {
						Method setMethod = ipnNotif.getClass().getDeclaredMethod("set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1), String.class);
						//field.set(ipnNotif, request.getParameter(field.getName()));
						setMethod.invoke(ipnNotif,request.getParameter(field.getName()));
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch( IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println(ipnNotif.getPayment_status().equals("Completed") );
		System.out.println(ipnNotif.getReceiver_email().equals("soldi7_1256933426_biz@hotmail.com"));
		System.out.println(sessionIds != null);
		System.out.println(sessionIds.contains(ipnNotif.getCustom()));
		if (ipnNotif.getPayment_status().equals("Completed") 
				&& userService.paypalCheckTxIsUnique(ipnNotif.getTxn_id())
				&& ipnNotif.getReceiver_email().equals("soldi7_1256933426_biz@hotmail.com")
				&& sessionIds != null
				&& sessionIds.contains(ipnNotif.getCustom())) {
			return true;	
		} else {
			return false;
		}
		
	}
	
	public String actionUpdateUser() {
		userService.saveUser(userInfoBean.getActiveUser());
		userInfoBean.setPasswordConfirm(null);
		FacesContext ctx = FacesContext.getCurrentInstance();
		msgInfo = LangUtil.getMessage(ctx, "page.user_myprofile.profilesaved");
		return "";
	}
	
	//==============================================
	//=========== PRODUCTS ACTIONS =================
	//==============================================
	
	public String actionAddToCart() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (userInfoBean.getActiveUser() != null) {
			Long objidParam = Long.valueOf(ctx.getExternalContext().getRequestParameterMap().get("objid"));
			if (objidParam != null) {
				Product product = productService.getProduct(objidParam);
				OrderLine ol = new OrderLine(product);
				int objIndex = userInfoBean.getShoppingCart().indexOf(ol);
				if (objIndex != -1) {
					userInfoBean.getShoppingCart().get(objIndex).incrementQuantity();
				} else {
					ol.setIndex(userInfoBean.getShoppingCart().size() + 1);
					userInfoBean.getShoppingCart().add(ol);
				}
				ctx.addMessage("siteinfo", LangUtil.getFacesMessage(ctx, "shopcart.productadded"));
			}
		} else {
			ctx.addMessage(null, LangUtil.getFacesMessage(ctx, "error.shopcart.usernotregistered"));
			return "/pages/user_signin";
		}
		return "";
	}
	
	public String actionPlusOneProduct() {
		Long objidParam = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objid"));
		if (objidParam != null) {
			for (OrderLine orderLine : userInfoBean.getShoppingCart()) {
				if (orderLine.getProduct().getId().equals(objidParam)) {
					orderLine.setQuantity(orderLine.getQuantity() + 1);
					break;
				}
			}
		}
		return "";
	}
	
	public String actionMinusOneProduct() {
		Long objidParam = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objid"));
		if (objidParam != null) {
			for (OrderLine orderLine : userInfoBean.getShoppingCart()) {
				if (orderLine.getProduct().getId().equals(objidParam)) {
					orderLine.setQuantity(orderLine.getQuantity() - 1);
					break;
				}
			}
		}
		return "";
	}
	
	public String actionRemoveProduct() {
		Long objidParam = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objid"));
		if (objidParam != null) {
			int toRemove = -1;
			for (int i = 0; i < userInfoBean.getShoppingCart().size(); i++) {
				if (userInfoBean.getShoppingCart().get(i).getProduct().getId().equals(objidParam)) {
					toRemove = i;
					break;
				}
			}
			if (toRemove != -1) {
				userInfoBean.getShoppingCart().remove(toRemove);
			}
		}
		return "";
	}
	
	public Double getShoppingCartTotal() {
		Double total = 0.0;
		for (OrderLine orderLine : userInfoBean.getShoppingCart()) {
			total = total + (orderLine.getQuantity() * orderLine.getProduct().getPrice());
		}
		return total;
	}
}

