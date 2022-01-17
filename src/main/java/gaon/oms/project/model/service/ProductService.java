package gaon.oms.project.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.ProductDAO;
import gaon.oms.project.model.dto.K7_CATEGORY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_OPTION_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_MENU_OMS;
import lombok.Setter;

@Service
public class ProductService {

	@Setter(onMethod_ = @Autowired)
	private ProductDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 상품 검색
	public List<K7_MENU_MASTER_OMS> productSearch(Map<String, Object> paramMap) {
		return dao.productSearch(paramMap, sqlSession);
	}

	// 행사 타입 가져오기
	public List<K7_REFERENCE_CODE_OMS> GetPolicyCode() {

		return dao.GetPolicyCode(sqlSession);
	}

	// 매장 내 상품 분류 가져오기
	public List<K7_CATEGORY_MASTER_OMS> GetCategory() {
		return dao.GetCategory(sqlSession);
	}

	// ################################DETAIL################################

	// 상품 기본 정보 가져오기
	public K7_MENU_MASTER_OMS getProductMasterDetailData(String plucd) {

		return dao.getProductMasterDetailData(plucd, sqlSession);
	}

	// 행사 상품 옵션 정보 가져오기
	public List<K7_MENU_OPTION_OMS> getProductOptionDetailData(String plucd) {

		return dao.getProductOptionDetailData(plucd, sqlSession);
	}

	// 매장 상품 정보 가져오기
	public K7_STORE_MENU_OMS getStoreProductDetailData(Map<String, Object> paramMap) {

		return dao.getStoreProductDetailData(paramMap, sqlSession);
	}

	// 매장 재고 정보 가져오기
	public K7_STORE_MENU_OMS getStoreStockDetailData(Map<String, Object> paramMap) {

		return dao.getStoreStockDetailData(paramMap, sqlSession);
	}

	// 마감 할인 정보 가져오기
	public K7_STORE_MENU_OMS getClosingSaleDetailData(Map<String, Object> paramMap) {

		return dao.getClosingSaleDetailData(paramMap, sqlSession);
	}

	// 상품 옵션 코드로 아이템 내역 가져오기
	public List<K7_MENU_OPTION_OMS> getProductItemData(String opcd) {

		return dao.getProductItemData(opcd, sqlSession);
	}

	// ################################Paging 처리################################

	public int getSearchProductTotal(Map<String, Object> paramMap) {

		return dao.getSearchProductTotal(paramMap, sqlSession);
	}

	// 카테고리 cd 이름으로 조회, 가져오기
	public String getCategoryCode(String ctgrNm) {
		return dao.getCategoryCode(ctgrNm, sqlSession);
	}

}
