package be.arthurius.web.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionIdListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("New session");
		@SuppressWarnings("unchecked")
		List<String> sessionIds = (List<String>) arg0.getSession().getServletContext().getAttribute("sessionIds");
		if (sessionIds == null) {
			sessionIds = new ArrayList<String>();
			sessionIds.add(arg0.getSession().getId());
		}
		arg0.getSession().getServletContext().setAttribute("sessionIds", sessionIds);
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Delete session");
		@SuppressWarnings("unchecked")
		List<String> sessionIds = (List<String>) arg0.getSession().getServletContext().getAttribute("sessionIds");
		if (sessionIds == null) {
			sessionIds = new ArrayList<String>();
		} else {
			sessionIds.remove(arg0.getSession().getId());
		}
		arg0.getSession().getServletContext().setAttribute("sessionIds", sessionIds);
	}

}
