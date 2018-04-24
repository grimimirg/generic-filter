package it.genericfilter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Utility class for conditions handling.
 * 
 * Conditions are tadily applyed based on declaration specified order.
 * 
 * If some conditions are defined for the same field, only the first one will be
 * taken.
 * 
 * Es:
 * 
 * .addCriterionFilterCondition(new CriterionFilterCondition("insertDateFrom",
 * "insertDateTo"){...});
 * 
 * .addCriterionFilterCondition(new
 * CriterionFilterCondition("insertDateTo"){...});
 * 
 * The second condition will not be applyed because field <b>insertDateTo</b> hs
 * been already defined in the first one.
 * 
 * @author Andrea_Grimandi
 *
 */
public class CriterionFilterUtil {

	private List<CriterionFilterCondition> criterionFilterConditions;

	private List<String> usedCriterionFilterNames;

	private List<CriterionFilterCondition> remainingFilterConditions;

	private ObjectNode htmlJsonFilter;

	/**
	 * 
	 */
	public CriterionFilterUtil() {
		this.criterionFilterConditions = new ArrayList<CriterionFilterCondition>();
		this.usedCriterionFilterNames = new ArrayList<String>();
		this.remainingFilterConditions = new ArrayList<CriterionFilterCondition>();
	}

	/**
	 * 
	 * @return
	 */
	public ObjectNode getHtmlJsonFilter() {
		return htmlJsonFilter;
	}

	/**
	 * 
	 * @param filters
	 */
	public void setHtmlJsonFilter(ObjectNode filters) {
		this.htmlJsonFilter = filters;
	}

	/**
	 * 
	 * @return
	 */
	public List<CriterionFilterCondition> getCriterionFilterConditions() {
		return criterionFilterConditions;
	}

	/**
	 * 
	 * @param criterionFilterConditions
	 */
	public void setCriterionFilterConditions(List<CriterionFilterCondition> criterionFilterConditions) {
		this.criterionFilterConditions = criterionFilterConditions;
	}

	/**
	 * 
	 * @param criterionFilterCondition
	 * @return
	 */
	public CriterionFilterUtil addCriterionFilterCondition(CriterionFilterCondition criterionFilterCondition) {
		this.criterionFilterConditions.add(criterionFilterCondition);
		return this;
	}

	/**
	 * Search for a condition by a field name
	 * 
	 * @param fieldName
	 * @return
	 */
	public CriterionFilterCondition getCriterionFilterConditionByFieldName(String fieldName) {
		for (CriterionFilterCondition criterionFilterCondition : this.criterionFilterConditions) {
			if (criterionFilterCondition.getFieldNames().contains(fieldName)) {
				this.usedCriterionFilterNames.addAll(criterionFilterCondition.getFieldNames());
				return criterionFilterCondition;
			}
			return null;
		}
		return null;
	}

	/**
	 * Search for every condition that is not been associated to any bean field
	 * 
	 * @return
	 */
	public List<CriterionFilterCondition> remainingCriterionFilterConditions() {
		for (CriterionFilterCondition criterionPair : this.criterionFilterConditions) {
			if (!this.usedCriterionFilterNames.containsAll(criterionPair.getFieldNames())
					|| criterionPair.getFieldNames().size() == 0) {
				this.remainingFilterConditions.add(criterionPair);
			}
		}
		return this.remainingFilterConditions;
	}

}
