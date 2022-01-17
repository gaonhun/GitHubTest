package gaon.oms.project.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gaon.oms.project.model.dao.UserDAO;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.K7_USER_AUTH_OMS;
import lombok.Setter;

@Service
public class UserService {

	@Setter(onMethod_ = @Autowired)
	private UserDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public K7_USER_ACCOUNT_OMS loginCustomer(K7_USER_ACCOUNT_OMS tu) {
		return dao.loginCustomer(sqlSession, tu);
	}

	public int UserInsert(K7_USER_ACCOUNT_OMS tu) {
		return dao.UserInsert(sqlSession, tu);
	}

	public int UserUpdate(K7_USER_ACCOUNT_OMS tbu) {
		return dao.UserUpdate(sqlSession, tbu);
	}

	public List<K7_USER_ACCOUNT_OMS> UserSearchList(Map<String, Object> paramMap) {
		return dao.userSearchList(sqlSession, paramMap);
	}

	public K7_USER_ACCOUNT_OMS UserDetail(String user_nm) {

		return dao.UserDetail(sqlSession, user_nm);
	}

	// ################################Paging 처리################################

	public int getSearchUserTotal(Map<String, Object> paramMap) {

		return dao.getSearchUserTotal(sqlSession, paramMap);
	}

	// 아이디 확인
	public int getUserCheck(String user_id) {
		return dao.getUserCheck(sqlSession, user_id);
	}

	// 사용자명 확인
	public int getNmCheck(String user_nm) {
		return dao.getNmCheck(sqlSession, user_nm);
	}

	// 인증번호 저장
	public int setSMSauthentication(K7_USER_ACCOUNT_OMS smsIns) {
		return dao.setSMSauthentication(sqlSession, smsIns);
	}

	// 인증번호 체크
	public K7_USER_ACCOUNT_OMS SMSauthCheck(K7_USER_ACCOUNT_OMS uao) {
		return dao.SMSauthCheck(sqlSession, uao);
	}

	public List<K7_USER_AUTH_OMS> getUserLevel() {
		return dao.getUserLevel(sqlSession);
	}

}
