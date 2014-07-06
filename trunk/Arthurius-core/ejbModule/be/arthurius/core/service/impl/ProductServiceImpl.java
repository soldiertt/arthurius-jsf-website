package be.arthurius.core.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.arthurius.core.dao.ProductDao;
import be.arthurius.core.model.Product;
import be.arthurius.core.service.ProductService;

@Stateless
public class ProductServiceImpl implements ProductService {

	@EJB
	ProductDao productDao;
	
	
	public List<Product> findFirstPageProducts() {
		return productDao.findFirstPageProducts();
	}

	public List<Product> findProductsByMark(String mark) {
		return productDao.findProductsByMark(mark);
	}

	public List<Product> findProductsByType(String type) {
		return productDao.findProductsByType(type);
	}

	public Product getProduct(Long productId) {
		return productDao.getProduct(productId);
	}

	public List<String> findAllHandles() {
		return productDao.findAllHandles();
	}

	public List<String> findAllMarks() {
		return productDao.findAllMarks();
	}

	public List<String> findAllSteels() {
		return productDao.findAllSteels();
	}

	public List<Product> searchProducts(String searchTerms) {
		return productDao.searchProducts(searchTerms);
	}

	public List<Product> searchProducts(String mark, String steel,
			String handle, Double startPrice, Double endPrice) {
		
		if ("allmarks".equals(mark)) {
			mark = null;
		}
		if ("allsteels".equals(steel)) {
			steel = null;
		}
		if ("allhandles".equals(handle)) {
			handle = null;
		}
		return productDao.searchProducts(mark, steel, handle, startPrice, endPrice);
	}
	
}
