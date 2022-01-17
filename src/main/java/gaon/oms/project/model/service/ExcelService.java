package gaon.oms.project.model.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.ExcelDAO;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import lombok.Setter;

@Service
public class ExcelService {
	@Setter(onMethod_ = @Autowired)
	private ExcelDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 매장 리스트 엑셀 다운로드
	public List<K7_COMPANY_MASTER_OMS> getStoreExcel(HashMap<String, Object> paramMap) {
		return dao.getStoreExcel(paramMap, sqlSession);
	}

	// 상품 리스트 엑셀 다운로드
	public List<K7_MENU_MASTER_OMS> getProductExcel(HashMap<String, Object> paramMap) {
		return dao.getProductExcel(paramMap, sqlSession);
	}

	// 주문 리스트 엑셀 다운로드
	public List<K7_ORDER_HEADER_OMS> getOrderExcel(HashMap<String, Object> paramMap) {
		return dao.getOrderExcel(paramMap, sqlSession);
	}

	// 매장 엑셀 업로드
	public int setStoreAllUpload(HashMap<String, Object> paramMap) {

		dao.setCompanyMasterUpload(paramMap, sqlSession);
		dao.setStoreOperationUpload(paramMap, sqlSession);
		dao.setStoreChannelUpload(paramMap, sqlSession);
		dao.setCompanyMaster_GDC(sqlSession);

		return dao.setStoreOperation_GDC(sqlSession);
	}

	// 메뉴 삭제 후 백업
	public int deleteAllMenu() {
		return dao.deleteAllMenu(sqlSession);

	}

	// SUB02_01
	public Object setAllProductUpload(HashMap<String, Object> paramMap) {
		dao.setCategoryUpdate(paramMap, sqlSession);
		return dao.setMenuUpdate(paramMap, sqlSession);
	}

	public Object setMenuUpdate_GDC() {
		return dao.setMenuUpdate_GDC(sqlSession);
	}

//	// 메뉴 카테고리 업로드
//	public int setCategoryUpdate(HashMap<String, Object> paramMap) {
//		return dao.setCategoryUpdate(paramMap, sqlSession);
//	}
//
//	// 메뉴 상품 업로드
//	public int setMenuUpdate(HashMap<String, Object> paramMap) {
//		return dao.setMenuUpdate(paramMap, sqlSession);
//	}
//
//	public Object setMenuUpdate_GDC() {
//		return dao.setMenuUpdate_GDC(sqlSession);
//	}

	// 일부 업로드

	// 메뉴 상품 업로드
	public Object setPartProductUpload(HashMap<String, Object> paramMap) {
		dao.setCategoryUpdate(paramMap, sqlSession);
		dao.setMenuPartUpdate(paramMap, sqlSession);
		dao.setMenuPartUpdate_GDC(paramMap, sqlSession);
		return dao.setCategoryUpdate_GDC(paramMap, sqlSession);
	}
//	public int setMenuPartUpdate(HashMap<String, Object> paramMap) {
//		return dao.setMenuPartUpdate(paramMap, sqlSession);
//	}
//
//	public Object setMenuPartUpdate_GDC(HashMap<String, Object> paramMap) {
//		return dao.setMenuPartUpdate_GDC(paramMap, sqlSession);
//	}
//
//	public Object setCategoryUpdate_GDC(HashMap<String, Object> paramMap) {
//		return dao.setCategoryUpdate_GDC(paramMap, sqlSession);
//	}



}
