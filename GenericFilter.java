package it.genericfilter;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Hibernate criteria generator
 * 
 * @author Andrea_Grimandi
 *
 * @param <E>
 */
public class GenericFilter<E> {

	/**
	 * 
	 */
	private Criteria criteria;

	/**
	 * 
	 * @param criteria
	 */
	public GenericFilter(Criteria criteria) {
		super();
		this.criteria = criteria;
	}

	/**
	 * Builds Hibernate criteria
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Criteria getCriteria(Criterion filter) throws Exception {
		return this.criteria.add(filter);
	}

	/**
	 * Builds Hibernate criteria
	 * 
	 * @param objFilter
	 * @return
	 * @throws Exception
	 */
	public Criteria getCriteria(E objFilter) throws Exception {
		if (objFilter != null) {
			for (Method method : Arrays.asList(objFilter.getClass().getDeclaredMethods())) {
				if (method.getName().contains("get")) {
					Object value = method.invoke(objFilter, (Object[]) null);
					if (value != null) {
						// nome del campo della classe preso a partire dai getter (in quanto siano
						// private e non accessibili tramite reflection)
						String fieldName = Introspector.decapitalize(method.getName().substring(3));
						// se stringa, ilike
						if (method.getReturnType().equals(String.class)) {
							this.criteria.add(Restrictions.ilike(fieldName, "%" + value.toString() + "%"));
						} else {
							this.criteria.add(Restrictions.eq(fieldName, value));
						}
					}
				}
			}
		}
		return this.criteria;
	}

	/**
	 * Builds Hibernate criteria
	 * 
	 * @param objFilter
	 * @param criterionFilter
	 * @return
	 * @throws Exception
	 */
	public Criteria getCriteria(E objFilter, CriterionFilterUtil criterionFilter) throws Exception {
		if (objFilter != null) {
			for (Method method : Arrays.asList(objFilter.getClass().getDeclaredMethods())) {
				if (method.getName().contains("get")) {
					Object value = method.invoke(objFilter, (Object[]) null);
					if (value != null) {
						// get the field class name by the getter method
						String fieldName = Introspector.decapitalize(method.getName().substring(3));

						CriterionFilterCondition criterionPair = criterionFilter
								.getCriterionFilterConditionByFieldName(fieldName);

						// custom condition on this field
						if (criterionPair != null) {
							Criterion criterion = criterionPair
									.getCriterionFilterCondition(criterionFilter.getHtmlJsonFilter());
							if (criterion != null) {
								this.criteria.add(criterion);
							}
						} else {
							// ilike default behaviour with string types
							if (method.getReturnType().equals(String.class)) {
								this.criteria.add(Restrictions.ilike(fieldName, "%" + value.toString() + "%"));
							} else {
								this.criteria.add(Restrictions.eq(fieldName, value));
							}
						}

					}
				}
			}

			this.getCriteria(criterionFilter);
		}

		return this.criteria;
	}

	/**
	 * Builds Hibernate criteria
	 * 
	 * @param criterionFilter
	 * @return
	 * @throws Exception
	 */
	public Criteria getCriteria(CriterionFilterUtil criterionFilter) throws Exception {
		// already used conditions list
		List<String> criterionUsed = new ArrayList<String>();

		for (CriterionFilterCondition criterionPair : criterionFilter.remainingCriterionFilterConditions()) {

			// conditions will be added only if:
			//
			// 1. Are not referred to any bean field then this type of filter must be always
			// applyed;
			//
			// 2. Are custom filter;
			//
			// 3. Are related to some bean field;
			boolean isFilterInRequest = false;

			for (String fieldName : criterionPair.getFieldNames()) {
				isFilterInRequest = criterionFilter.getHtmlJsonFilter().toString().contains(fieldName);

				if (!isFilterInRequest) {
					break;
				}
			}

			// Avoid multiple conditons on the same field
			boolean alreadyUsedFilter = false;

			for (String udesFilter : criterionUsed) {
				for (String incomingFilter : criterionPair.getFieldNames()) {
					if (udesFilter.equals(incomingFilter)) {
						alreadyUsedFilter = true;
						break;
					}
				}

				if (alreadyUsedFilter) {
					break;
				}
			}

			// 1. size() == 0 -> condition always applyed
			//
			// 2. isFilterInRequest -> The field appear in the request
			//
			// 3. alreadyUsedFilter -> Avoid the same condition to be applyed more than once
			if ((criterionPair.getFieldNames().size() == 0 || isFilterInRequest) && !alreadyUsedFilter) {
				Criterion criterion = criterionPair.getCriterionFilterCondition(criterionFilter.getHtmlJsonFilter());
				if (criterion != null) {
					criterionUsed.addAll(criterionPair.getFieldNames());
					this.criteria.add(criterion);
				}
			}
		}
		return this.criteria;
	}

}
