package be.arthurius.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Product implements Serializable, Comparable<Product> {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 7493165636543302253L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	@Column(name = "version")
	private int version = 0;
	
	@Column(name="ref", unique=true, nullable=false, length=25)
	private String ref;
	
	@Column(name="type", nullable=false, length=25)
	private String type;
	
	@Column(name="marque", nullable=false, length=30)
	private String mark;
	
	@Column(name="name", nullable=false, length=50)	
	private String name;
	
	@Column(name="description", length=3000)
	private String description;
	
	@Column(name="picture", length=50)
	private String picture;
	
	@Column(name="manche", length=50)
	private String handle;
	
	@Column(name="acier", length=50)
	private String steel;
	
	@Column(name="size", length=50)
	private String size;
	
	/**
	 * if contains "ac" => actu on first page !
	 */
	@Column(name="promo", length=10)
	private String promo;
	
	@Column(name="price", nullable=false)
	private Double price;
	
	@Column(name="piece", length=100)
	private String moreinfo;
	
	/**
	 * Default constructor.
	 */
	public Product() {
		// Default constructor.
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

	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMark() {
		return mark;
	}
	
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getHandle() {
		return handle;
	}
	
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public String getSteel() {
		return steel;
	}
	
	public void setSteel(String steel) {
		this.steel = steel;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getPromo() {
		return promo;
	}
	
	public void setPromo(String promo) {
		this.promo = promo;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getMoreinfo() {
		return moreinfo;
	}
	
	public void setMoreinfo(String moreinfo) {
		this.moreinfo = moreinfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int compareTo(Product o) {
		return - o.getName().compareToIgnoreCase(this.getName());
	}
	
}
