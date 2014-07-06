package be.arthurius.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Section implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -2608775167521981228L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	@Column(name = "version")
	private int version = 0;
	 
	@Column(nullable=false, length=100)
	private String type;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Column(nullable=false, length=100)
	private String titleNl;
	
	@Column(nullable=false, length=100)
	private String titleEn;
	
	@Column(length=100)
	private String parent;

	/**
	 * Default constructor.
	 */
	public Section() {
		//Default constructor.
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleNl() {
		return titleNl;
	}

	public void setTitleNl(String titleNl) {
		this.titleNl = titleNl;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
}
