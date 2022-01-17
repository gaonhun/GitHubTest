package gaon.oms.project.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;

@Repository
public class ExcelDAO {

	// 매장 리스트 엑셀 다운로드
	public List<K7_COMPANY_MASTER_OMS> getStoreExcel(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("excel.oms_sub01_01excel", paramMap);
	}

	// 상품 리스트 엑셀 다운로드
	public List<K7_MENU_MASTER_OMS> getProductExcel(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("excel.oms_sub02_01excel", paramMap);
	}

	// 주문 리스트 엑셀 다운로드
	public List<K7_ORDER_HEADER_OMS> getOrderExcel(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("excel.oms_sub03_01excel", paramMap);
	}

	// 매장 업로드
	public int setCompanyMasterUpload(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setCompanyMasterUpload 진행 ============== ");
		return sqlSession.insert("excel.setCompanyMasterUpload", paramMap);
	}

	public int setStoreOperationUpload(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setStoreOperationUpload 진행 ============== ");
		return sqlSession.insert("excel.setStoreOperationUpload", paramMap);
	}

	public int setStoreChannelUpload(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setStoreChannelUpload 진행 ============== ");
		return sqlSession.insert("excel.setStoreChannelUpload", paramMap);
	}

	public int setCompanyMaster_GDC(SqlSessionTemplate sqlSession) {
		System.out.println("setCompanyMaster_GDC 진행 ============== ");
		return sqlSession.insert("excel.setCompanyMaster_GDC");
	}

	public int setStoreOperation_GDC(SqlSessionTemplate sqlSession) {
		System.out.println("setStoreOperation_GDC 진행 ============== ");
		return sqlSession.insert("excel.setStoreOperation_GDC");
	}

	// 메뉴 업로드
	// 메뉴 전체 삭제
	public int deleteAllMenu(SqlSessionTemplate sqlSession) {
		System.out.println("deleteAllMenu 진행 ============== ");
		return sqlSession.delete("excel.deleteAllMenu");
	}

	// 카테고리 업로드
	public int setCategoryUpdate(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setCategoryUpdate 진행 ============== ");
		return sqlSession.insert("excel.setCategoryUpdate", paramMap);
	}

	// 상품 업로드
	public int setMenuUpdate(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setMenuUpdate 진행 ============== ");
		return sqlSession.insert("excel.setMenuUpdate", paramMap);
	}

	public Object setMenuUpdate_GDC(SqlSessionTemplate sqlSession) {
		System.out.println("setMenuUpdate_GDC 진행 ============== ");
		return sqlSession.insert("excel.setMenuUpdate_GDC");
	}

	// 메뉴 일부 업로드
	// 상품 업로드
	public Object setCategoryUpdate_GDC(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setCategoryUpdate_GDC 진행 ============== ");
		return sqlSession.insert("excel.setCategoryUpdate_GDC", paramMap);
	}
	
	public int setMenuPartUpdate(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setMenuPartUpdate 진행 ============== ");
		return sqlSession.insert("excel.setMenuPartUpdate", paramMap);
	}

	public Object setMenuPartUpdate_GDC(HashMap<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		System.out.println("setMenuPartUpdate_GDC 진행 ============== ");
		return sqlSession.insert("excel.setMenuPartUpdate_GDC", paramMap);
	}


}