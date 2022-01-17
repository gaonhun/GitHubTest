package gaon.oms.project.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.StoreDAO;
import gaon.oms.project.model.dto.K7_CHANNEL_STORE_OPERATION_OMS;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_OPERATION_OMS;
import lombok.Setter;

@Service
public class StoreService {

	@Setter(onMethod_ = @Autowired)
	private StoreDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public List<K7_COMPANY_MASTER_OMS> storeSearch(Map<String, Object> paramMap) {
		return dao.storeSearch(paramMap, sqlSession);
	}

	// 매장 상태 리스트
	public List<K7_REFERENCE_CODE_OMS> GetStoreState() {
		return dao.GetStoreState(sqlSession);
	}

	// ##################Detail0101##################
	// 가맹점 정보 불러오기
	public K7_COMPANY_MASTER_OMS getStoreDetailData(String stocd) {

		return dao.getStoreDetailData(stocd, sqlSession);
	}

	// 매장 운영 정보 불러오기
	public K7_STORE_OPERATION_OMS getStoreOperationDetailData(String stocd) {

		return dao.getStoreOperationDetailData(stocd, sqlSession);
	}

	// 배달 대행 운영 정보 불러오기
	public List<K7_COMPANY_MASTER_OMS> getDeliveryChannelOperationDetailData(String stocd) {

		return dao.getDeliveryChannelOperationDetailData(stocd, sqlSession);
	}

	// 채널 매장 정보 불러오기
	public List<K7_CHANNEL_STORE_OPERATION_OMS> getChannelStoreOperation(String stocd) {
		return dao.getChannelStoreOperation(stocd, sqlSession);
	}

	// ################################Paging 처리################################

	public int getSearchStoreTotal(Map<String, Object> paramMap) {

		return dao.getSearchStoreTotal(paramMap, sqlSession);
	}

	//LIST
	public List<K7_REFERENCE_CODE_OMS> GetChannelFlag() {
		return dao.GetChannelFlag(sqlSession);
	}

	public List<K7_COMPANY_MASTER_OMS> GetDeliveryList() {
		return dao.GetDeliveryList(sqlSession);
	}

}
