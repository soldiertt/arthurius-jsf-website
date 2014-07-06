package be.arthurius.core.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import be.arthurius.core.dao.SectionDao;
import be.arthurius.core.model.Section;

@Stateless
public class SectionDaoImpl implements SectionDao {

	@PersistenceContext(unitName="arthurius")
	EntityManager manager;
	
	public Section findSectionByType(String type) {
		Section section = (Section) manager.createQuery("FROM Section WHERE type = :type").setParameter("type", type).getSingleResult();
		return section;
	}

	public Section getSection(Long sectionId) {
		return manager.find(Section.class, sectionId);
	}

	@SuppressWarnings("unchecked")
	public List<Section> getRootSections() {
		List<Section> sectionList = (List<Section>) manager.createQuery("FROM Section WHERE parent is null").getResultList();
		return sectionList;
	}

	@SuppressWarnings("unchecked")
	public List<Section> getSubSections(String parent) {
		List<Section> sectionList = (List<Section>) manager.createQuery("FROM Section WHERE parent = :type").setParameter("type", parent).getResultList();
		return sectionList;
	}
}
