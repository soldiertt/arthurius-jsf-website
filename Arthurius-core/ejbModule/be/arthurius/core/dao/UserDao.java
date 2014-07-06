package be.arthurius.core.dao;

import javax.ejb.Local;

import be.arthurius.core.model.IPNNotification;
import be.arthurius.core.model.User;
import be.arthurius.core.model.UserRole;

/**
 * 
 * @author admin
 *
 */
@Local
public interface UserDao {

	User getUser(Long userId);
	
	void saveUser(User user);
	
	void saveUserRole(UserRole userRole);
	
	void deleteUser(User user);
	
	User getUserByEmail(String email);
	
	User getUserByUserName(String userName);
	
	boolean paypalCheckTxIsUnique(String txn_id);
	
	void paypalSaveNotification(IPNNotification ipnNotif);
}
