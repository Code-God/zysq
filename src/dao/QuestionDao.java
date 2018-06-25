package dao;

import model.bo.Question;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("questionDao")
public class QuestionDao extends EnhancedHibernateDaoSupport<Question> {

	@Override
	protected String getEntityName() {
		return Question.class.getName();
	}
}
