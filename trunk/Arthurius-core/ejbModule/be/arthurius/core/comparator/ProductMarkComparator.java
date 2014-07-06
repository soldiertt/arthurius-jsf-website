package be.arthurius.core.comparator;

import java.util.Comparator;

import be.arthurius.core.model.Product;

public class ProductMarkComparator implements Comparator<Product> {

	public int compare(Product o1, Product o2) {
		return o1.getMark().trim().compareTo(o2.getMark().trim());
	}

}
