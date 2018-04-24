package it.genericfilter;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Handles most common operations and perform queries
 * 
 * @author Andrea_Grimandi
 *
 * @param <E>
 */
@Repository
public class GenericDao<E> {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 */
	private Class<?> E;

	/**
	 * 
	 * @param E
	 */
	public GenericDao<E> init(Class<?> E) {
		this.E = E;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(this.E).list();
	}

	/**
	 * 
	 * @param obj
	 */
	public void create(E obj) {
		sessionFactory.getCurrentSession().save(obj);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E read(Integer id) {
		return (E) sessionFactory.getCurrentSession().createCriteria(this.E).add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * 
	 * @param id
	 * @param obj
	 */
	public void update(Integer id, E obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from " + this.E.getSimpleName() + " where id = :id")
				.setInteger("id", id).executeUpdate();
	}

	// ****************************************************************************************************************

	/**
	 * 
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<E> filteredList(E entityFilter) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(this.E);
		return new GenericFilter<E>(criteria).getCriteria(entityFilter).list();
	}

	/**
	 * 
	 * @param criterionFilter
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<E> filteredList(CriterionFilterUtil customFilter) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(this.E);
		return new GenericFilter<E>(criteria).getCriteria(customFilter).list();
	}

	/**
	 * 
	 * @param entityFilter
	 * @param customFilter
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<E> filteredList(E entityFilter, CriterionFilterUtil customFilter) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(this.E);
		return new GenericFilter<E>(criteria).getCriteria(entityFilter, customFilter).list();
	}

}
