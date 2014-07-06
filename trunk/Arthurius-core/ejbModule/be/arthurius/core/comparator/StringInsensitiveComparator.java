package be.arthurius.core.comparator;

import java.util.Comparator;

public class StringInsensitiveComparator implements Comparator<String> {

	public int compare(String o1, String o2) {
		return o1.trim().compareToIgnoreCase(o2.trim());
	}

}
