package gaon.oms.project.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.K7_USER_AUTH_OMS;

@Repository
public class UserDAO {

	public K7_USER_ACCOUNT_OMS loginCustomer(SqlSessionTemplate sqlSession, K7_USER_ACCOUNT_OMS tu) {
		return sqlSession.selectOne("user.loginCustomer", tu);
	}

	public int UserInsert(SqlSessionTemplate sqlSession, K7_USER_ACCOUNT_OMS tu) {
		return sqlSession.insert("user.userInsert", tu);
	}

	public int UserUpdate(SqlSessionTemplate sqlSession, K7_USER_ACCOUNT_OMS tbu) {
		return sqlSession.insert("user.userUpdate", tbu);
	}

	public List<K7_USER_ACCOUNT_OMS> userSearchList(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		return sqlSession.selectList("user.userSearchList", paramMap);
	}

	public K7_USER_ACCOUNT_OMS UserDetail(SqlSessionTemplate sqlSession, String user_nm) {

		return sqlSession.selectOne("user.userDetail", user_nm);
	}

	// ################################Paging 처리################################

	public int getSearchUserTotal(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectOne("user.getSearchUserTotal", paramMap);
	}

	// 아이디 체크
	public int getUserCheck(SqlSessionTemplate sqlSession, String user_id) {

		return sqlSession.selectOne("user.getUserCheck", user_id);
	}

	public int getNmCheck(SqlSessionTemplate sqlSession, String user_nm) {

		return sqlSession.selectOne("user.getNmCheck", user_nm);
	}

	// 인증번호 저장
	public int setSMSauthentication(SqlSessionTemplate sqlSession, K7_USER_ACCOUNT_OMS smsIns) {

		return sqlSession.update("setSMSauthentication", smsIns);
	}

	// 인증번호 체크
	public K7_USER_ACCOUNT_OMS SMSauthCheck(SqlSessionTemplate sqlSession, K7_USER_ACCOUNT_OMS uao) {

		return sqlSession.selectOne("SMSauthCheck", uao);
	}

	public List<K7_USER_AUTH_OMS> getUserLevel(SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("getUserLevel");
	}
}
