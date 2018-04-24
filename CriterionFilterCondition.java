package it.genericfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author Andrea_Grimandi
 *
 */
public abstract class CriterionFilterCondition {

	private List<String> fieldNames;

	/**
	 * 
	 * @param fieldName
	 */
	public CriterionFilterCondition() {
		super();
		this.fieldNames = new ArrayList<String>();
	}

	/**
	 * 
	 * @param fieldName
	 */
	public CriterionFilterCondition(String... fieldName) {
		super();
		this.fieldNames = Arrays.asList(fieldName);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getFieldNames() {
		return fieldNames;
	}

	/**
	 * 
	 * @param fieldName
	 */
	public void setFieldNames(String... fieldName) {
		this.fieldNames = Arrays.asList(fieldName);
	}

	/**
	 * Defines the condition logic
	 * 
	 * @param filters
	 *            json conditions from the frontend
	 * @return
	 */
	public Criterion getCriterionFilterCondition(ObjectNode filters) {
		return null;
	}

}
