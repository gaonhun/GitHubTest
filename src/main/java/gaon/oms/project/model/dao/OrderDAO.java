package gaon.oms.project.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HISTORY_OMS;
import gaon.oms.project.model.dto.K7_ORDER_MENU_OMS;
import gaon.oms.project.model.dto.K7_ORDER_OPTION_ITEM_OMS;

@Repository("oDAO")
public class OrderDAO {

	// 주문 내역 검색
	public List<K7_ORDER_HEADER_OMS> orderSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("order.orderSearch", paramMap);
	}

	// 주문 상세 정보
	public K7_ORDER_HEADER_OMS orderDetail(SqlSessionTemplate sqlSession, K7_ORDER_HEADER_OMS tState) {
		return sqlSession.selectOne("order.orderDetail", tState);
	}

	// 주문 메뉴 정보
	public List<K7_ORDER_MENU_OMS> orderMenu(SqlSessionTemplate sqlSession, K7_ORDER_HEADER_OMS tState) {
		return sqlSession.selectList("order.orderMenu", tState);
	}

	// 주문 메뉴 아이템 가져오기
	public List<K7_ORDER_OPTION_ITEM_OMS> getOrderMenuItem(SqlSessionTemplate sqlSession,
			Map<String, Object> paramMap) {
		return sqlSession.selectList("order.getOrderMenuItem", paramMap);
	}

	// 주문 이력 정보
	public List<K7_ORDER_HISTORY_OMS> orderHistory(SqlSessionTemplate sqlSession, K7_ORDER_HEADER_OMS tState) {
		return sqlSession.selectList("order.orderHistory", tState);
	}

	// ################################Paging 처리################################

	public int getSearchOrderTotal(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectOne("order.getSearchOrderTotal", paramMap);
	}
}
