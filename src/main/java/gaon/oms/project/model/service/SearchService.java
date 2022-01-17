package gaon.oms.project.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.SearchDAO;
import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import lombok.Setter;

@Service
public class SearchService {

	@Setter(onMethod_ = @Autowired)
	private SearchDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 매장 상세 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchStore(Criteria cri) {

		return dao.getSearchStore(cri, sqlSession);
	}

	// 채널사 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchChannel(Criteria cri) {

		return dao.getSearchChannel(cri, sqlSession);
	}

	// 배달 대행 검색
	public List<K7_COMPANY_MASTER_OMS> getSearchDelivery(Criteria cri) {

		return dao.getSearchDelivery(cri, sqlSession);
	}

	// 상품 검색
	public List<K7_MENU_MASTER_OMS> getSearchProduct(Criteria cri) {

		return dao.getSearchProduct(cri, sqlSession);
	}

	// ######################################PAGING##########################################
	// StoreSearch 총 게시글 개수 확인
	public int getSearchStoreTotal(Criteria cri) {

		return dao.getSearchStoreTotal(cri, sqlSession);
	}

	// ChannelSearch 총 게시글 개수 확인
	public int getSearchChannelTotal(Criteria cri) {

		return dao.getSearchChannelTotal(cri, sqlSession);
	}

	public int getSearchDeliveryTotal(Criteria cri) {

		return dao.getSearchDeliveryTotal(cri, sqlSession);
	}

	// ProductSearch 총 게시글 개수 확인
	public int getSearchProductTotal(Criteria cri) throws Exception {

		return dao.getSearchProductTotal(cri, sqlSession);
	}

}