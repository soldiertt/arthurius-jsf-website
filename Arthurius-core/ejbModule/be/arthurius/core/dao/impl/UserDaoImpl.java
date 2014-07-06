package be.arthurius.core.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import be.arthurius.core.dao.UserDao;
import be.arthurius.core.model.IPNNotification;
import be.arthurius.core.model.User;
import be.arthurius.core.model.UserRole;

@Stateless
public class UserDaoImpl implements UserDao {

	@PersistenceContext(unitName="arthurius")
	EntityManager manager;
	
	public void deleteUser(User user) {
		manager.remove(user);
	}

	public User getUser(Long userId) {
		User user = manager.find(User.class, userId);
		return user;
	}

	public void saveUser(User user) {
		manager.merge(user);
	}

	public void saveUserRole(UserRole userRole) {
		manager.merge(userRole);
	}

	public User getUserByEmail(String email) {
		try  {
			return (User) manager.createQuery("FROM User where email = :email").setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User getUserByUserName(String userName) {
		try  {
			return (User) manager.createQuery("FROM User where userName = :username").setParameter("username", userName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean paypalCheckTxIsUnique(String txn_id) {
		try  {
			IPNNotification notif = (IPNNotification) manager.createQuery("FROM IPNNotification where txn_id = :txnid").setParameter("txnid", txn_id).getSingleResult();
		} catch (NoResultException e) {
			return true;
		}
		return false;
	}

	public void paypalSaveNotification(IPNNotification ipnNotif) {
		manager.merge(ipnNotif);
	}

}
