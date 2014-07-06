package be.arthurius.web.bean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import be.arthurius.core.comparator.ProductPriceComparator;
import be.arthurius.core.model.Product;
import be.arthurius.core.model.Section;
import be.arthurius.core.service.ProductService;
import be.arthurius.core.service.SectionService;

import com.sun.faces.util.MessageFactory;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7840522602143690896L;
	private static final String BOX_VIEW_MODE = "box";
	private static final String LIST_VIEW_MODE = "list";
	private static final String DEFAULT_VIEW_MODE = BOX_VIEW_MODE;
	
	@EJB
	private SectionService sectionService;
	@EJB
	private ProductService productService;
	
	private List<Section> categories;
	private List<Product> previewProducts;
	private List<Product> oneMarkProducts;
	private Section	activeCategory;
	private Section	activeSubCategory;
	private String activeMark;
	private List<Section> subCategories;
	private Product browseProduct;
	private String viewMode;
	private String sortColumn;
	
	public List<Section> getCategories() {
		if (categories == null) {
			categories = sectionService.getRootSections();
		}
		return categories;
	}
	
	public List<Product> getPreviewProducts() {
		return previewProducts;
	}

	public Section getActiveCategory() {
		return activeCategory;
	}

	public Section getActiveSubCategory() {
		return activeSubCategory;
	}

	public String getActiveMark() {
		return activeMark;
	}

	public List<Section> getSubCategories() {
		return subCategories;
	}

	public Product getBrowseProduct() {
		return browseProduct;
	}

	public List<Product> getOneMarkProducts() {
		return oneMarkProducts;
	}

	public String getViewMode() {
		if (viewMode == null) {
			viewMode = DEFAULT_VIEW_MODE;
		}
		return viewMode;
	}

	public String getSortColumn() {
		if (sortColumn == null) {
			sortColumn = "name";
		}
		return sortColumn;
	}

	public String getMainTitle() {
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		viewId = viewId.replace("/pages/", "").replace(".xhtml", "");
		FacesMessage message = MessageFactory.getMessage("title." + viewId);
		return message.getSummary();
	}
	
	public boolean getProductImageIsFullSize() {
		boolean isFullSize = false;
		try {
			if (browseProduct != null && browseProduct.getPicture() != null) {
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				InputStream imageStream = servletContext.getResourceAsStream("/images/products/" + browseProduct.getType() + "/" + browseProduct.getPicture());
				BufferedImage productImage = ImageIO.read(imageStream);
				if (productImage.getWidth() <= 470) {
					isFullSize = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isFullSize;
	}
	
	public String actionSwitchViewMode() {
		if (viewMode.equals(BOX_VIEW_MODE)) {
			viewMode = LIST_VIEW_MODE;
		} else {
			viewMode = BOX_VIEW_MODE;
		}
		return "";
	}
	
	public String actionBrowseCateg() {
		sortColumn = null;
		String typeParam = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");
		if (typeParam != null) {
			activeCategory = sectionService.findSectionByType(typeParam);
			activeSubCategory = null;
			subCategories = sectionService.getSubSections(typeParam);
		} else {
			typeParam = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("subtype");
			activeSubCategory = sectionService.findSectionByType(typeParam);
		}
		previewProducts = productService.findProductsByType(typeParam);
		return "browse_categ";
	}
	
	public String actionBrowseProduct() {
		String objidParam = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objid");
		if (objidParam != null) {
			browseProduct = productService.getProduct(Long.parseLong(objidParam));
			return "browse_product";
		} else {
			return "";
		}
	}
	
	public String actionBrowseMark() {
		sortColumn = null;
		activeMark = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("markname");
		if (activeMark != null) {
			oneMarkProducts = productService.findProductsByMark(activeMark);
			return "browse_mark";
		} else {
			return "";
		}
	}
	
	public void listenerSortProducts(ActionEvent e) {
		sortColumn = (String) e.getComponent().getAttributes().get("sort");
		if (sortColumn.equals("price")) {
			Collections.sort(previewProducts, new ProductPriceComparator());
		} else if (sortColumn.equals("name")) {
			Collections.sort(previewProducts);
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void listenerSortProductsMark(ActionEvent e) {
		sortColumn = (String) e.getComponent().getAttributes().get("sort");
		if (sortColumn.equals("price")) {
			Collections.sort(oneMarkProducts, new ProductPriceComparator());
		} else if (sortColumn.equals("name")) {
			Collections.sort(oneMarkProducts);
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
}
