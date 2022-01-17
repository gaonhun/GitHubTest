package gaon.oms.project.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_CATEGORY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_OPTION_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_MENU_OMS;

@Repository
public class ProductDAO {

	// 상품 검색
	public List<K7_MENU_MASTER_OMS> productSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("product.productSearch", paramMap);
	}

	// 행사 타입 가져오기
	public List<K7_REFERENCE_CODE_OMS> GetPolicyCode(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("product.GetPolicyCode");
	}

	// 매장 내 상품 분류 가져오기
	public List<K7_CATEGORY_MASTER_OMS> GetCategory(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("product.GetCategory");
	}

	// ################################DETAIL################################

	// 상품 기본 정보 가져오기
	public K7_MENU_MASTER_OMS getProductMasterDetailData(String plucd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("product.ProductMasterDetail", plucd);
	}

	// 행사 상품 옵션 정보 가져오기
	public List<K7_MENU_OPTION_OMS> getProductOptionDetailData(String plucd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("product.ProductOptionDetail", plucd);
	}

	// 매장 상품 운영 정보 가져오기
	public K7_STORE_MENU_OMS getStoreProductDetailData(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("product.StoreProductDetail", paramMap);
	}

	// 매장 재고 정보 가져오기
	public K7_STORE_MENU_OMS getStoreStockDetailData(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("product.StoreStockDetail", paramMap);
	}

	// 마감 할인 정보 가져오기
	public K7_STORE_MENU_OMS getClosingSaleDetailData(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("product.ClosingSaleDetail", paramMap);
	}

	// 상품 옵션 코드로 아이템 내역 가져오기
	public List<K7_MENU_OPTION_OMS> getProductItemData(String opcd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("product.getProductItem", opcd);
	}

	// ################################Paging 처리################################

	public int getSearchProductTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("product.getSearchProductTotal", paramMap);
	}

	public String getCategoryCode(String ctgrNm, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("product.getCategoryCode", ctgrNm);
	}

}
