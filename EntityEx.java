package it.genericfilter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EntityEx {

	@Column
	private String prop1;

	@Column
	private String prop2;

	@Column
	private String prop3;

	@Column
	private String prop4;

	@Column
	private String prop5;

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public String getProp4() {
		return prop4;
	}

	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}

	public String getProp5() {
		return prop5;
	}

	public void setProp5(String prop5) {
		this.prop5 = prop5;
	}

}
