package gaon.oms.project.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.ChannelDAO;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import lombok.Setter;

@Service
public class ChannelService {

	@Setter(onMethod_ = @Autowired)
	private ChannelDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public List<K7_COMPANY_MASTER_OMS> ChannelSearch(Map<String, Object> paramMap) {
		return dao.ChannelSearch(paramMap, sqlSession);
	}

	public List<K7_REFERENCE_CODE_OMS> GetCPNState() {
		return dao.GetCPNState(sqlSession);
	}

	// DETAIL
	public K7_COMPANY_MASTER_OMS getCPN_Detail(String cpncd) {
		return dao.getCPN_Detail(cpncd, sqlSession);
	}

	// ################################Paging 처리################################

	public int getOrderChannelTotal(Map<String, Object> paramMap) {
		return dao.getOrderChannelTotal(paramMap, sqlSession);
	}

	// ################################ 채널 등록 ##############################
	// 배달, 채널사 등록
	public Object setUpdateChannelData(HashMap<String, Object> map) {
		return dao.setUpdateChannelData(map, sqlSession);
	}

	public Object setInsetChannelData(HashMap<String, Object> map) {
		return dao.setInsetChannelData(map, sqlSession);
	}

}
