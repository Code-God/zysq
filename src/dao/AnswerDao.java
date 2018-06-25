package dao;

import model.bo.Answers;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("answerDao")
public class AnswerDao extends EnhancedHibernateDaoSupport<Answers> {

	@Override
	protected String getEntityName() {
		return Answers.class.getName();
	}
}
