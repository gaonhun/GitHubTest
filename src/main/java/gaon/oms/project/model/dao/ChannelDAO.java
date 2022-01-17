package gaon.oms.project.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;

@Repository
public class ChannelDAO {

	public List<K7_COMPANY_MASTER_OMS> ChannelSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.ChannelSearch", paramMap);
	}

	public List<K7_REFERENCE_CODE_OMS> GetCPNState(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.GetCPNState");
	}

	// DATAIL
	public K7_COMPANY_MASTER_OMS getCPN_Detail(String cpncd, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getCPN_Detail", cpncd);
	}

	// ################################Paging 처리################################

	public int getOrderChannelTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getOrderChannelTotal", paramMap);
	}

	// ################################ 채널 등록 ##############################
	// 배달, 채널사 등록
	public Object setUpdateChannelData(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
		return sqlSession.update("Channel.setUpdateChannelData", map);
	}

	public Object setInsetChannelData(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
		return sqlSession.insert("Channel.setInsetChannelData", map);
	}
}
