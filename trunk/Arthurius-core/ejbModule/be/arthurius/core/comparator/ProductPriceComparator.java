package be.arthurius.core.comparator;

import java.util.Comparator;

import be.arthurius.core.model.Product;

public class ProductPriceComparator implements Comparator<Product> {

	public int compare(Product o1, Product o2) {
		return o1.getPrice().compareTo(o2.getPrice());
	}

}
