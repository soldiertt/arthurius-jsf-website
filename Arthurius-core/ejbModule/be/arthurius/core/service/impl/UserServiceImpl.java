package be.arthurius.core.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.arthurius.core.dao.UserDao;
import be.arthurius.core.model.IPNNotification;
import be.arthurius.core.model.User;
import be.arthurius.core.model.UserRole;
import be.arthurius.core.service.UserService;

@Stateless
public class UserServiceImpl implements UserService {

	@EJB
	UserDao userDao;
	
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	public User getUser(Long userId) {
		return userDao.getUser(userId);
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
		userDao.saveUserRole(new UserRole(user.getUserName()));
	}

	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	public boolean paypalCheckTxIsUnique(String txn_id) {
		return userDao.paypalCheckTxIsUnique(txn_id);
	}

	public void paypalSaveNotification(IPNNotification ipnNotif) {
		userDao.paypalSaveNotification(ipnNotif);
	}
}
