package it.genericfilter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles most common operations and perform queries
 * 
 * @author Andrea_Grimandi
 *
 * @param <E>
 */
@Service
public class GenericService<E> {

	@Autowired
	private GenericDao<E> genericDao;

	/**
	 * 
	 */
	private Class<?> E;

	/**
	 * 
	 * @param E
	 */
	public GenericService<E> init(Class<?> E) {
		this.E = E;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	@Transactional
	public List<E> getAll() {
		return genericDao.init(this.E).getAll();
	}

	/**
	 * 
	 * @param json
	 * @throws Exception
	 */
	@Transactional
	public void create(E obj) throws Exception {
		genericDao.init(this.E).create(obj);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public E read(Integer id) {
		return genericDao.init(this.E).read(id);
	}

	/**
	 * 
	 * @param id
	 * @param json
	 * @throws Exception
	 */
	@Transactional
	public void update(Integer id, E obj) throws Exception {
		genericDao.init(this.E).update(id, obj);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Integer delete(Integer id) {
		return genericDao.init(this.E).delete(id);
	}

	// ****************************************************************************************************************

	/**
	 * 
	 * @param entityFilter
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<E> filteredList(E entityFilter) throws Exception {
		return genericDao.init(this.E).filteredList(entityFilter);
	}

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<E> filteredList(CriterionFilter customFilter) throws Exception {
		return genericDao.init(this.E).filteredList(customFilter.getFilter());
	}

	/**
	 * 
	 * @param entity
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<E> filteredList(E entityFilter, CriterionFilter customFilter) throws Exception {
		customFilter.getFilter().setHtmlJsonFilter(
				new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(entityFilter), ObjectNode.class));
		return genericDao.init(this.E).filteredList(entityFilter, customFilter.getFilter());
	}

	/**
	 * 
	 * @param entityFilter
	 * @param customFilter
	 * @return
	 * @throws Exception
	 */
	public List<E> filteredList(String entityFilter, CriterionFilter customFilter) throws Exception {
		customFilter.getFilter().setHtmlJsonFilter(new ObjectMapper().readValue(entityFilter, ObjectNode.class));
		return genericDao.init(this.E).filteredList(this.fromJsonToEntity(entityFilter), customFilter.getFilter());
	}

	// ****************************************************************************************************************

	/**
	 * 
	 * @param json
	 * @return
	 */
	public E fromJsonToEntity(String json) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JavaType type = mapper.getTypeFactory().constructType(this.E);
		try {
			return mapper.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
