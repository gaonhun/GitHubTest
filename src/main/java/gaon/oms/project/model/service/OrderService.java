package gaon.oms.project.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.OrderDAO;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HISTORY_OMS;
import gaon.oms.project.model.dto.K7_ORDER_MENU_OMS;
import gaon.oms.project.model.dto.K7_ORDER_OPTION_ITEM_OMS;
import lombok.Setter;

@Service
public class OrderService {

	@Setter(onMethod_ = @Autowired)
	private OrderDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 주문 내역 검색
	public List<K7_ORDER_HEADER_OMS> orderSearch(Map<String, Object> paramMap) {
		return dao.orderSearch(paramMap, sqlSession);
	}

	// ################################DETAIL################################

	// 주문 상세 정보
	public K7_ORDER_HEADER_OMS orderDetail(K7_ORDER_HEADER_OMS tState) {
		return dao.orderDetail(sqlSession, tState);
	}

	// 주문 메뉴 정보
	public List<K7_ORDER_MENU_OMS> orderMenu(K7_ORDER_HEADER_OMS tState) {
		return dao.orderMenu(sqlSession, tState);
	}

	// 주문 메뉴 아이템 가져오기
	public List<K7_ORDER_OPTION_ITEM_OMS> getOrderMenuItem(Map<String, Object> paramMap) {
		return dao.getOrderMenuItem(sqlSession, paramMap);
	}

	// 주문 이력 정보
	public List<K7_ORDER_HISTORY_OMS> orderHistory(K7_ORDER_HEADER_OMS tState) {
		return dao.orderHistory(sqlSession, tState);
	}

	// ################################Paging 처리################################

	public int getSearchOrderTotal(Map<String, Object> paramMap) {

		return dao.getSearchOrderTotal(sqlSession, paramMap);
	}
}
