package dao.youzhen;

import model.bo.Columns;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * 栏目表DAO 
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("columnDao")
public class ColumnDao extends EnhancedHibernateDaoSupport<Columns> {

	@Override
	protected String getEntityName() {
		return Columns.class.getName();
	}
}
