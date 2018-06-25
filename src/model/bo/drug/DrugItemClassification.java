package model.bo.drug;

import java.io.Serializable;
/**
 * 疾病(项目)大分类
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_item_classification" lazy="false"
 */
public class DrugItemClassification implements Serializable {
	private static final long serialVersionUID = 6230215932334240991L;
	private Long id;
	/**
	 * 类型名称
	 */
	private String classificationName;
	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @hibernate.property column="classification_name" type="string"
	 */
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	
	
	
}
