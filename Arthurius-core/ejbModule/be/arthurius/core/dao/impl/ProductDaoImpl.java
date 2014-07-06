package be.arthurius.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import be.arthurius.core.dao.ProductDao;
import be.arthurius.core.model.Product;

@Stateless
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext(unitName="arthurius")
	EntityManager manager;
	
	
	@SuppressWarnings("unchecked")
	public List<Product> findFirstPageProducts() {
		List<Product> productList = (List<Product>) manager.createQuery("FROM Product WHERE promo LIKE '%ac%' order by name").getResultList();
		return productList;
	}

	@SuppressWarnings("unchecked")
	public List<Product> findProductsByMark(String mark) {
		List<Product> productList = (List<Product>) manager.createQuery("FROM Product WHERE mark = :mark order by name").setParameter("mark", mark).getResultList();
		return productList;
	}

	@SuppressWarnings("unchecked")
	public List<Product> findProductsByType(String type) {
		List<Product> productList = (List<Product>) manager.createQuery("FROM Product WHERE type = :type order by name").setParameter("type", type).getResultList();
		return productList;
	}

	public Product getProduct(Long productId) {
		return manager.find(Product.class, productId);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllHandles() {
		List<String> allHandles = (List<String>) manager.createQuery("SELECT distinct(handle) FROM Product WHERE handle is not null").getResultList();
		return allHandles;
	}

	@SuppressWarnings("unchecked")
	public List<String> findAllMarks() {
		List<String> allMarks = (List<String>) manager.createQuery("SELECT distinct(mark) FROM Product WHERE mark is not null").getResultList();
		return allMarks;
	}

	@SuppressWarnings("unchecked")
	public List<String> findAllSteels() {
		List<String> allSteels = (List<String>) manager.createQuery("SELECT distinct(steel) FROM Product WHERE steel is not null").getResultList();
		return allSteels;
	}

	@SuppressWarnings("unchecked")
	public List<Product> searchProducts(String searchTerms) {
		searchTerms = "%" + searchTerms + "%";
		String sqlQuery = "FROM Product WHERE mark like :terms or steel like :terms or handle like :terms or name like :terms or description like :terms";
		Query searchQuery = manager.createQuery(sqlQuery);
		searchQuery.setParameter("terms", searchTerms);
		List<Product> foundProducts = (List<Product>) searchQuery.getResultList();
		return foundProducts;
	}

	@SuppressWarnings("unchecked")
	public List<Product> searchProducts(String mark, String steel,
			String handle, Double startPrice, Double endPrice) {
		boolean firstArg = true;
		String sqlQuery = "FROM Product WHERE ";
		
		//------- GENERATE QUERY -----------
		if (mark != null) {
			if (firstArg) { firstArg=false; } else { sqlQuery = sqlQuery.concat(" AND "); }
			sqlQuery = sqlQuery.concat("mark = :mark");
		}
		if (steel != null) {
			if (firstArg) { firstArg=false; } else { sqlQuery = sqlQuery.concat(" AND "); }
			sqlQuery = sqlQuery.concat("steel = :steel");
		}
		if (handle != null) {
			if (firstArg) { firstArg=false; } else { sqlQuery = sqlQuery.concat(" AND "); }
			sqlQuery = sqlQuery.concat("handle = :handle");
		}
		if (startPrice != null) {
			if (firstArg) { firstArg=false; } else { sqlQuery = sqlQuery.concat(" AND "); }
			sqlQuery = sqlQuery.concat("price >= :startPrice");
		}
		if (endPrice != null) {
			if (firstArg) { firstArg=false; } else { sqlQuery = sqlQuery.concat(" AND "); }
			sqlQuery = sqlQuery.concat("price <= :endPrice");
		}
		Query searchQuery = manager.createQuery(sqlQuery);
		
		//------- GENERATE PARAMS -----------
		if (mark != null) {
			searchQuery.setParameter("mark", mark);
		}
		if (steel != null) {
			searchQuery.setParameter("steel", steel);
		}
		if (handle != null) {
			searchQuery.setParameter("handle", handle);
		}
		if (startPrice != null) {
			searchQuery.setParameter("startPrice", startPrice);
		}
		if (endPrice != null) {
			searchQuery.setParameter("endPrice", endPrice);
		}
		List<Product> foundProducts = (List<Product>) searchQuery.getResultList();
		return foundProducts;
	}
	
	
}
