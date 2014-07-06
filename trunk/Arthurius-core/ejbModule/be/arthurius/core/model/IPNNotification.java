package be.arthurius.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IPNNotification {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	// ========================================
	// Variables IPN/PDT : Informations de base
	// ========================================
	@Column(length=40)
	private String address_city;
	
	@Column(length=64)
	private String address_country;
	
	@Column(length=2)
	private String address_country_code;
	
	@Column(length=128)
	private String address_name;
	
	@Column(length=40)
	private String address_state;
	
	/**
	 *  confirmed / unconfirmed
	 */
	@Column(length=11)
	private String address_status;
	
	@Column(length=200)
	private String address_street;
	
	@Column(length=20)
	private String address_zip;
	
	@Column(length=64)
	private String first_name;
	
	@Column(length=64)
	private String last_name;
	
	@Column(length=127)
	private String payer_business_name;
	
	@Column(length=127)
	private String payer_email;
	
	@Column(length=13)
	private String payer_id;
	
	/**
	 * verified/unverified
	 */
	@Column(length=10)
	private String payer_status;
	
	@Column(length=2)
	private String residence_country;
	
	// ==========================================
	// Variables IPN/PDT : Informations de base 2
	// ==========================================
	
	@Column(length=127)
	private String business;
	
	/**
	 * check for item_name1, item_name2,...
	 */
	private String item_names;

	/**
	 * check for quantity1, quantity2,...
	 */
	private String item_quantities;
	
	@Column(length=127)
	private String receiver_email; 
	
	@Column(length=13)
	private String receiver_id;
	
	// ===========================================================
	// Variables IPN/PDT : Informations avancées et personnalisées
	// ===========================================================
	
	@Column(length=255)
	private String custom;
	
	@Column(length=255)
	private String memo;
	
	// ===========================================================
	// Variables IPN/PDT : Paiements sur site marchand, Paiements 
	// sur site marchand Pro et informations sur les remboursements
	// ===========================================================
	
	private String auth_id;
	
	private String auth_exp;
	
	private String auth_status;
	
	private String num_cart_items;
	
	private String payment_date;
	
	private String payment_status;
	
	private String payment_type;
	
	private String pending_reason;
	
	private String reason_code;
	
	private String txn_id;
	
	private String txn_type;
	
	private String auth_amount;
	
	// =============================================================
	// Variables IPN/PDT : Informations sur les devises et le change
	// =============================================================
	
	private String mc_fee;
	
	private String mc_gross;

	// =================================
	// ======== GETTER / SETTER ========
	// =================================
	
	public String getAddress_city() {
		return address_city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddress_city(String address_city) {
		this.address_city = address_city;
	}

	public String getAddress_country() {
		return address_country;
	}

	public void setAddress_country(String address_country) {
		this.address_country = address_country;
	}

	public String getAddress_country_code() {
		return address_country_code;
	}

	public void setAddress_country_code(String address_country_code) {
		this.address_country_code = address_country_code;
	}

	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}

	public String getAddress_state() {
		return address_state;
	}

	public void setAddress_state(String address_state) {
		this.address_state = address_state;
	}

	public String getAddress_status() {
		return address_status;
	}

	public void setAddress_status(String address_status) {
		this.address_status = address_status;
	}

	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}

	public String getAddress_zip() {
		return address_zip;
	}

	public void setAddress_zip(String address_zip) {
		this.address_zip = address_zip;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPayer_business_name() {
		return payer_business_name;
	}

	public void setPayer_business_name(String payer_business_name) {
		this.payer_business_name = payer_business_name;
	}

	public String getPayer_email() {
		return payer_email;
	}

	public void setPayer_email(String payer_email) {
		this.payer_email = payer_email;
	}

	public String getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(String payer_id) {
		this.payer_id = payer_id;
	}

	public String getPayer_status() {
		return payer_status;
	}

	public void setPayer_status(String payer_status) {
		this.payer_status = payer_status;
	}

	public String getResidence_country() {
		return residence_country;
	}

	public void setResidence_country(String residence_country) {
		this.residence_country = residence_country;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getItem_names() {
		return item_names;
	}

	public void setItem_names(String item_names) {
		this.item_names = item_names;
	}

	public String getItem_quantities() {
		return item_quantities;
	}

	public void setItem_quantities(String item_quantities) {
		this.item_quantities = item_quantities;
	}

	public String getReceiver_email() {
		return receiver_email;
	}

	public void setReceiver_email(String receiver_email) {
		this.receiver_email = receiver_email;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}

	public String getAuth_exp() {
		return auth_exp;
	}

	public void setAuth_exp(String auth_exp) {
		this.auth_exp = auth_exp;
	}

	public String getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}

	public String getNum_cart_items() {
		return num_cart_items;
	}

	public void setNum_cart_items(String num_cart_items) {
		this.num_cart_items = num_cart_items;
	}

	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getPending_reason() {
		return pending_reason;
	}

	public void setPending_reason(String pending_reason) {
		this.pending_reason = pending_reason;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	public String getTxn_type() {
		return txn_type;
	}

	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}

	public String getAuth_amount() {
		return auth_amount;
	}

	public void setAuth_amount(String auth_amount) {
		this.auth_amount = auth_amount;
	}

	public String getMc_fee() {
		return mc_fee;
	}

	public void setMc_fee(String mc_fee) {
		this.mc_fee = mc_fee;
	}

	public String getMc_gross() {
		return mc_gross;
	}

	public void setMc_gross(String mc_gross) {
		this.mc_gross = mc_gross;
	}
	
}
