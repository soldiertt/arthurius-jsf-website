package be.arthurius.core.service;

import java.util.List;

import javax.ejb.Local;

import be.arthurius.core.model.Product;

@Local
public interface ProductService {

	Product getProduct(Long productId);
	
	List<Product> findFirstPageProducts();
	
	List<Product> findProductsByMark(String mark);
	
	List<Product> findProductsByType(String type);
	
	List<String> findAllMarks();
	
	List<String> findAllSteels();
	
	List<String> findAllHandles();
	
	List<Product> searchProducts(String searchTerms);
	
	List<Product> searchProducts(String mark, String steel, String handle, Double startPrice, Double endPrice);
}
