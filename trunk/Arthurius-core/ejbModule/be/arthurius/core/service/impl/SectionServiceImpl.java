package be.arthurius.core.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.arthurius.core.dao.SectionDao;
import be.arthurius.core.model.Section;
import be.arthurius.core.service.SectionService;

@Stateless
public class SectionServiceImpl implements SectionService {

	@EJB
	SectionDao sectionDao;
	
	public Section findSectionByType(String type) {
		return sectionDao.findSectionByType(type);
	}

	public Section getSection(Long sectionId) {
		return sectionDao.getSection(sectionId);
	}

	public List<Section> getRootSections() {
		return sectionDao.getRootSections();
	}

	public List<Section> getSubSections(String parent) {
		return sectionDao.getSubSections(parent);
	}

}
