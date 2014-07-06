package be.arthurius.web.bean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
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
import be.arthurius.web.util.LangUtil;

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
	private List<Product> firstPageProducts;
	private List<Product> oneMarkProducts;
	private Section	activeCategory;
	private Section	activeSubCategory;
	private String activeMark;
	private List<Section> subCategories;
	private Product browseProduct;
	private String viewMode;
	private String sortColumn;
	private String language;
	
	public List<Section> getCategories() {
		if (categories == null) {
			categories = sectionService.getRootSections();
		}
		return categories;
	}
	
	public List<Product> getPreviewProducts() {
		return previewProducts;
	}

	public List<Product> getFirstPageProducts() {
		if (firstPageProducts == null) {
			firstPageProducts = productService.findFirstPageProducts();
		}
		return firstPageProducts;
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

	public void setActiveMark(String activeMark) {
		this.activeMark = activeMark;
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

	public String getLanguage() {
		if (language == null) {
			Locale currenLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			language = currenLocale.getLanguage();
		}
		return language;
	}

	public String getMainTitle() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String viewId = ctx.getViewRoot().getViewId();
		viewId = viewId.replace("/pages/", "").replace("customer/", "").replace(".xhtml", "");
		String message = LangUtil.getMessage(ctx, "title." + viewId);
		return message;
	}
	
	public boolean getProductImageIsFullSize() {
		boolean isFullSize = false;
		try {
			if (browseProduct != null && browseProduct.getPicture() != null) {
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				InputStream imageStream = servletContext.getResourceAsStream("/images/products/" + browseProduct.getType() + "/" + browseProduct.getPicture());
				if (imageStream != null) {
					BufferedImage productImage = ImageIO.read(imageStream);
					if (productImage.getWidth() <= 470) {
						isFullSize = true;
					}
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
	
	public String actionSwitchLanguage() {
		String langParam = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("language");
		language = langParam;
		if (langParam.equals("en")) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
		} else if (langParam.equals("nl")) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("nl"));
		} else {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("fr"));
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
		return "/pages/browse_categ";
	}
	
	public String actionBrowseProduct() {
		String objidParam = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objid");
		if (objidParam != null) {
			browseProduct = productService.getProduct(Long.parseLong(objidParam));
			return "/pages/browse_product";
		} else {
			return "";
		}
	}
	
	public String actionBrowseMark() {
		sortColumn = null;
		activeMark = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("markname");
		if (activeMark != null) {
			oneMarkProducts = productService.findProductsByMark(activeMark);
			return "/pages/browse_mark";
		} else {
			return "";
		}
	}
	
	public String actionBrowseMarkFromBanner() {
		if (activeMark != null) {
			oneMarkProducts = productService.findProductsByMark(activeMark);
			return "/pages/browse_mark";
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
