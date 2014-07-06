package be.arthurius.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -2608775167521981228L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, length=50)
	private String userName;
	
	@Column(nullable=false, length=50)
	private String userRole;
	
	/**
	 * Default constructor.
	 */
	public UserRole() {
		//Default constructor.
	}

	public UserRole(String userName) {
		this.userName = userName;
		this.userRole = "loginUser";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
