package gaon.oms.project.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.MainDAO;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import lombok.Setter;

@Service
public class MainService {

	@Setter(onMethod_ = @Autowired)
	private MainDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public List<K7_ORDER_HEADER_OMS> selectTodayCountList() {
		return dao.selectTodayCountList(sqlSession);
	}

	public List<K7_ORDER_HEADER_OMS> selectYesCountList() {
		return dao.selectYesCountList(sqlSession);
	}

	// 배송채널명
	public List<K7_COMPANY_MASTER_OMS> selectDeliveryNumber() {
		return dao.selectDeliveryNumber(sqlSession);
	}

//	주문채널명
	public List<K7_COMPANY_MASTER_OMS> selectFranchiseNumber() {
		return dao.selectFranchiseNumber(sqlSession);
	}

}
