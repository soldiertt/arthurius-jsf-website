package be.arthurius.core.dao;

import java.util.List;

import javax.ejb.Local;

import be.arthurius.core.model.Section;

/**
 * 
 * @author admin
 *
 */
@Local
public interface SectionDao {

	Section getSection(Long sectionId);
	
	Section findSectionByType(String type);
	
	List<Section> getRootSections();
	
	List<Section> getSubSections(String parent);
	
}
