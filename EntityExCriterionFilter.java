package it.genericfilter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntityExCriterionFilter implements CriterionFilter {

	public CriterionFilterUtil getFilter() {
		return new CriterionFilterUtil().addCriterionFilterCondition(new CriterionFilterCondition("prop1", "prop3") {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop1", "123456");
			}
		}).addCriterionFilterCondition(new CriterionFilterCondition() {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop5", 444);
			}
		});
	}

}
