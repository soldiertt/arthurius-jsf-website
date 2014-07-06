package be.arthurius.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import be.arthurius.core.utils.ArthuriusUtils;

@Entity
public class User implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -2608775167521981228L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, length=50, unique=true)
	private String userName;
	
	@Column(nullable=false, length=50)
	private String password;
	
	@Column(nullable=false, length=100)
	private String firstName;
	
	@Column(nullable=false, length=100)
	private String lastName;

	@Column(nullable=false, length=255)
	private String addrStreet;
	
	private Integer addrStreetNum;
	
	@Column(length=10)
	private String addrPostBox;
	
	@Column(nullable=false)
	private Integer addrPostCode;
	
	@Column(nullable=false, length=255)
	private String addrCity;
	
	@Column(nullable=false, length=50)
	private String addrCountry;
	
	@Column(nullable=false, length=255, unique=true)
	private String email;
	
	@Column(length=255)
	private String phoneNumber;
	
	@Column(length=255)
	private String chuckCode;
	
	@Column(nullable=false)
	private Boolean validated;
	
	/**
	 * Default constructor.
	 */
	public User() {
		//Default constructor.
	}
	
	public User(boolean initialize) {
		if (initialize) {
			this.chuckCode = ArthuriusUtils.getRandomChuck();
			this.validated = false;
		}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public Integer getAddrStreetNum() {
		return addrStreetNum;
	}

	public void setAddrStreetNum(Integer addrStreetNum) {
		this.addrStreetNum = addrStreetNum;
	}

	public String getAddrPostBox() {
		return addrPostBox;
	}

	public void setAddrPostBox(String addrPostBox) {
		this.addrPostBox = addrPostBox;
	}

	public Integer getAddrPostCode() {
		return addrPostCode;
	}

	public void setAddrPostCode(Integer addrPostCode) {
		this.addrPostCode = addrPostCode;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrCountry() {
		return addrCountry;
	}

	public void setAddrCountry(String addrCountry) {
		this.addrCountry = addrCountry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getChuckCode() {
		return chuckCode;
	}

	public void setChuckCode(String chuckCode) {
		this.chuckCode = chuckCode;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

}
