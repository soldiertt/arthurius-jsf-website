package be.arthurius.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sun.faces.util.MessageFactory;

import be.arthurius.core.comparator.StringInsensitiveComparator;
import be.arthurius.core.model.Product;
import be.arthurius.core.service.ProductService;

@ManagedBean
@SessionScoped
public class ProductSearchBean implements Serializable {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 5267168259296195487L;
	
	@EJB
	private ProductService productService;
	
	private String selectedMark;
	private List<SelectItem> baseAllMarks;
	private List<SelectItem> allMarks;
	private String selectedSteel;
	private List<SelectItem> allSteels;
	private String selectedHandle;
	private List<SelectItem> allHandles;
	private Double startPrice;
	private Double endPrice;
	private List<Product> searchResults;
	private String msgInfo;
	
	public String getSelectedMark() {
		return selectedMark;
	}

	public void setSelectedMark(String selectedMark) {
		this.selectedMark = selectedMark;
	}

	public String getSelectedSteel() {
		return selectedSteel;
	}

	public void setSelectedSteel(String selectedSteel) {
		this.selectedSteel = selectedSteel;
	}

	public String getSelectedHandle() {
		return selectedHandle;
	}

	public void setSelectedHandle(String selectedHandle) {
		this.selectedHandle = selectedHandle;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public List<SelectItem> getBaseAllMarks() {
		if (baseAllMarks == null) {
			baseAllMarks = new ArrayList<SelectItem>();
			List<String> allMarksTmp = productService.findAllMarks();
			Collections.sort(allMarksTmp, new StringInsensitiveComparator());
			for (String mark : allMarksTmp) {
				baseAllMarks.add(new SelectItem(mark, mark.trim()));
			}
		}
		return baseAllMarks;
	}
	
	public List<SelectItem> getAllMarks() {
		if (allMarks == null) {
			allMarks = new ArrayList<SelectItem>();
			FacesMessage message = MessageFactory.getMessage("page.search.allmarks");
			allMarks.add(new SelectItem("allmarks", message.getSummary()));
			allMarks.addAll(this.getBaseAllMarks());
		}
		return allMarks;
	}

	public List<SelectItem> getAllSteels() {
		if (allSteels == null) {
			allSteels = new ArrayList<SelectItem>();
			List<String> allSteelsTmp = productService.findAllSteels();
			Collections.sort(allSteelsTmp, new StringInsensitiveComparator());
			FacesMessage message = MessageFactory.getMessage("page.search.allsteels");
			allSteels.add(new SelectItem("allsteels", message.getSummary()));
			for (String steel : allSteelsTmp) {
				allSteels.add(new SelectItem(steel, steel.trim().toLowerCase()));
			}
		}
		return allSteels;
	}

	public List<SelectItem> getAllHandles() {
		if (allHandles == null) {
			allHandles = new ArrayList<SelectItem>();
			List<String> allHandlesTmp = productService.findAllHandles();
			Collections.sort(allHandlesTmp, new StringInsensitiveComparator());
			FacesMessage message = MessageFactory.getMessage("page.search.allhandles");
			allHandles.add(new SelectItem("allhandles", message.getSummary()));
			for (String handle : allHandlesTmp) {
				allHandles.add(new SelectItem(handle, handle.trim().toLowerCase()));
			}
		}
		return allHandles;
	}

	public Double getStartPrice() {
		if (startPrice == null) {
			startPrice = new Double(0.0);
		}
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		if (endPrice == null) {
			endPrice = new Double(100.0);
		}
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	public List<Product> getSearchResults() {
		return searchResults;
	}

	public String actionSearch() {
		searchResults = productService.searchProducts(this.selectedMark, 
				this.selectedSteel, this.selectedHandle,
				this.startPrice, this.endPrice);
		if (searchResults == null || searchResults.size() == 0) {
			msgInfo = "No results !";
		} else {
			msgInfo = "";
		}
		return "";
	}
	
	public String actionSearchReset() {
		searchResults = null;
		setSelectedMark("allmarks");
		setSelectedSteel("allsteels");
		setSelectedHandle("allhandles");
		setStartPrice(0.0);
		setEndPrice(100.0);
		msgInfo="";
		return "";
	}
}
