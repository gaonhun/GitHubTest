package gaon.oms.project.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;

@Repository
public class MainDAO {

	public List<K7_ORDER_HEADER_OMS> selectTodayCountList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectTodayCountList");
	}

	public List<K7_ORDER_HEADER_OMS> selectYesCountList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectYesCountList");
	}

	public List<K7_COMPANY_MASTER_OMS> selectDeliveryNumber(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectDeliveryNumber");
	}

	public List<K7_COMPANY_MASTER_OMS> selectFranchiseNumber(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectFranchiseNumber");
	}

}
