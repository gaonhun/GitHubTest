package gaon.oms.project.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_CHANNEL_STORE_OPERATION_OMS;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_OPERATION_OMS;

@Repository
public class StoreDAO {

	public List<K7_COMPANY_MASTER_OMS> storeSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.storeSearch", paramMap);
	}

	public List<K7_REFERENCE_CODE_OMS> GetStoreState(SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.GetReferenceCode");
	}

	// 가맹점 정보 불러오기
	public K7_COMPANY_MASTER_OMS getStoreDetailData(String stocd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.storeDetail", stocd);
	}

	// 매장 운영 정보 불러오기
	public K7_STORE_OPERATION_OMS getStoreOperationDetailData(String stocd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.storeOpertaionDetail", stocd);
	}

	// 배달 대행 운영 정보 불러오기
	public List<K7_COMPANY_MASTER_OMS> getDeliveryChannelOperationDetailData(String stocd,
			SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.DeliveryChannelOperationDetail", stocd);
	}

	// 채널 매장 정보 불러오기
	public List<K7_CHANNEL_STORE_OPERATION_OMS> getChannelStoreOperation(String stocd, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.getChannelStoreOperation", stocd);
	}

	// ################################Paging 처리################################

	public int getSearchStoreTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.getSearchStoreTotal", paramMap);
	}

	public List<K7_REFERENCE_CODE_OMS> GetChannelFlag(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.GetChannelFlag");
	}

	public List<K7_COMPANY_MASTER_OMS> GetDeliveryList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.GetDeliveryList");
	}

}
