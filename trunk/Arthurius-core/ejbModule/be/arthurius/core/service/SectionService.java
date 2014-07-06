package be.arthurius.core.service;

import java.util.List;

import javax.ejb.Local;

import be.arthurius.core.model.Section;

@Local
public interface SectionService {

	Section getSection(Long sectionId);
	
	Section findSectionByType(String type);
	
	List<Section> getRootSections();
	
	List<Section> getSubSections(String parent);
}
