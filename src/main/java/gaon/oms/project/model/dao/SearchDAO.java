package gaon.oms.project.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;

@Repository
public class SearchDAO {

	// 매장 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchStore(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchStore", cri);
	}

	// 채널사 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchChannel(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchChannel", cri);
	}

	// 배달 대행 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchDelivery(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchDelivery", cri);
	}

	// 상품 검색
	public List<K7_MENU_MASTER_OMS> getSearchProduct(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchProduct", cri);
	}

	// ###################################PAGING#####################################
	// StoreSearch 총 게시글 개수 확인
	public int getSearchStoreTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchStoreTotal", cri);
	}

	// ChannelSearch 총 게시글 개수 확인
	public int getSearchChannelTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchChannelTotal", cri);
	}

	// DeliverySearch 총 게시글 개수 확인
	public int getSearchDeliveryTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchDeliveryTotal", cri);
	}

	// ProductSearch 총 게시글 개수 확인
	public int getSearchProductTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchProductTotal", cri);
	}

}
