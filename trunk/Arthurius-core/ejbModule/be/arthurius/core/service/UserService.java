package be.arthurius.core.service;

import javax.ejb.Local;

import be.arthurius.core.model.IPNNotification;
import be.arthurius.core.model.User;

/**
 * 
 * @author admin
 *
 */
@Local
public interface UserService {

	User getUser(Long userId);
	
	void saveUser(User user);
	
	void deleteUser(User user);
	
	User getUserByEmail(String email);
	
	User getUserByUserName(String userName);
	
	boolean paypalCheckTxIsUnique(String txn_id);
	
	void paypalSaveNotification(IPNNotification ipnNotif);
}
