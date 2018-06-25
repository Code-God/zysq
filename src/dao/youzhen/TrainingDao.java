package dao.youzhen;

import model.bo.Training;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("trainingDao")
public class TrainingDao extends EnhancedHibernateDaoSupport<Training> {

	@Override
	protected String getEntityName() {
		return Training.class.getName();
	}
}
