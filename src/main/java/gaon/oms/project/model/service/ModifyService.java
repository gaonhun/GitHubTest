package gaon.oms.project.model.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.ModifyDAO;
import lombok.Setter;

@Service
public class ModifyService {

	@Setter(onMethod_ = @Autowired)
	private ModifyDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// ############################ 수정모드 ############################

	// ================== DETAIL 웹 수정 ==================
	// 0101
	public Object setUpdateSub01_01detail(HashMap<String, Object> map) {
		return dao.setUpdateSub01_01detail(sqlSession, map);
	}

	// 0201
	public Object setUpdateSub02_01detail(HashMap<String, Object> map) {
		return dao.setUpdateSub02_01detail(sqlSession, map);
	}

	public Object setChannelStoreOper(HashMap<String, Object> paramMap) {
		return dao.setChannelStoreOper(sqlSession, paramMap);
	}

	public Object setUpdateChannelData(HashMap<String, Object> map) {
		return dao.setUpdateChannelData(sqlSession, map);
	}
}
