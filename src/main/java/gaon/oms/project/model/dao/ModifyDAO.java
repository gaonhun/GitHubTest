package gaon.oms.project.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ModifyDAO {
	// ############################ 수정모드 ############################

	// ================== DETAIL 웹 수정 ==================
	public Object setUpdateSub01_01detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateSub01_01detail", map);
	}

	public Object setUpdateSub02_01detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateSub02_01detail", map);
	}

	public Object setChannelStoreOper(SqlSessionTemplate sqlSession, HashMap<String, Object> paramMap) {
		return sqlSession.update("modify.setChannelStoreOper", paramMap);
	}

	public Object setUpdateChannelData(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateChannelData", map);
	}
}
