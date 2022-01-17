package gaon.oms.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.common.Sha256Util;
import gaon.oms.project.common.UrlNotFoundException;
import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.K7_USER_AUTH_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.UserService;
import lombok.Setter;

@Controller
@SessionAttributes("UAccount")
public class UserController {

	@Setter(onMethod_ = @Autowired)
	private UserService service;

	@Autowired
	@Qualifier("Sha256Util")
	private Sha256Util enc;

	private Socket sop;

	private PrintWriter writer;

	private int chkCnt = 1;

	@RequestMapping(value = "/logincheck")
	@ResponseBody
	public ModelAndView loginCustomer(K7_USER_ACCOUNT_OMS uao, @RequestParam String ACCOUNT,
			@RequestParam String PASSWORD, ModelAndView mav) throws IOException {
		uao.setACCOUNT(ACCOUNT);
		uao.setPASSWORD(PASSWORD);

		K7_USER_ACCOUNT_OMS lgnCheck = service.loginCustomer(uao);

		if (lgnCheck != null) {
			String smsAuth = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));

			K7_USER_ACCOUNT_OMS smsIns = new K7_USER_ACCOUNT_OMS();

			smsIns.setACCOUNT(ACCOUNT);
			smsIns.setPASSWORD(PASSWORD);
			smsIns.setAUTH_NO(smsAuth);

			// SMS 인증번호 업데이트
			service.setSMSauthentication(smsIns);

			// 현재 날짜
			Date today = new Date();
			SimpleDateFormat nowDt = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat nowTm = new SimpleDateFormat("HHmmss");

			// SMS 전송
			String trData = "S101"; // 업무구분(4)
			trData += "1100"; // 전문번호(4)
			trData += nowDt.format(today); // 전송시각(8)
			trData += nowTm.format(today);// 시분초(6)
			trData += "0000"; // 응답코드(4)
			trData += " "; // 거래구분코드(1)
			trData += StringUtils.rightPad(lgnCheck.getPHONE(), 20, " ");// 수신자전화번호(20)
			trData += "0221872700          "; // 발신자전화번호(20)
			trData += "OMS SMS 인증번호  "; // 한글(18)
			trData += StringUtils.rightPad(smsAuth, 82, " "); // 인증번호(82)
			trData += "                    "; // SMS KEY(20)
			trData += "2000"; // 대분류(4)
			trData += "1116"; // 중분류(4)
			trData += StringUtils.rightPad(lgnCheck.getACCOUNT(), 12, " "); // 소분류-사용자ID(12)

//			try {
//				sop = new Socket("172.31.28.9", 4011);
//
//				System.out.println(sop.getInetAddress().getHostAddress() + " 연결됨");
//				BufferedOutputStream writer = new BufferedOutputStream(sop.getOutputStream());
//				// writer = new PrintWriter(sop.getOutputStream());
//
//				writer.write(trData.getBytes("euc-kr"));
//				writer.flush();
//
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//			} finally { // 소켓 닫기 (연결 끊기)
//				try {
//					if (sop != null) {
//						sop.close();
//					}
//					if (writer != null) {
//						writer.close();
//					}
//
//				} catch (IOException e) {
//					System.out.println(e.getMessage());
//				}
//			}
			mav.addObject("logStat", true);
			mav.addObject("tempAc", lgnCheck);
		} else {
			mav.addObject("logStat", false);
		}
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/oms_confirm")
	public String oms_confirm() throws IOException {

		return "oms_confirm";
	}

	@RequestMapping(value = "/smsCheck")
	@ResponseBody
	public ModelAndView smsCheck(HttpSession session, @RequestParam String smsNo, @RequestParam String acc,
			@RequestParam String pwd, ModelAndView mav) throws IOException {
		K7_USER_ACCOUNT_OMS uao = new K7_USER_ACCOUNT_OMS();

		uao.setACCOUNT(acc);
		uao.setPASSWORD(pwd);
		uao.setAUTH_NO(smsNo);

		K7_USER_ACCOUNT_OMS smsCheck = service.SMSauthCheck(uao);

		if (smsCheck != null) {
			// 세션 추가
			session.setAttribute("UAccount", smsCheck); // sms check 인증 값 user info -> 권한레벨 USER_LV

			mav.addObject("smsStat", true);
		} else {
			if (chkCnt == 5) {
				mav.addObject("smsStat", "error");

				chkCnt = 1;
			} else {
				mav.addObject("smsStat", false);
				chkCnt++;
			}
		}
		mav.setViewName("jsonView");

		return mav;

	}

	@RequestMapping(value = "/logoutCustomer")
	public String memberLogout(@ModelAttribute("UAccount") K7_USER_ACCOUNT_OMS UAccount, SessionStatus sessionStatus) {
		sessionStatus.setComplete();

		return "redirect:/";
	}

	@RequestMapping("/oms_sub05_01")
	public String oms_sub05_01(HttpSession session) {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		return "/oms_sub05_01";
	}

	@RequestMapping(value = "/oms_sub05_01_process")
	@ResponseBody
	public ModelAndView UserSearch(String userNm, Criteria cri, @RequestParam Map<String, Object> paramMap,
			ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("userNm", userNm);
		paramMap.put("cri", cri);

		List<K7_USER_ACCOUNT_OMS> tuaccount = service.UserSearchList(paramMap);

		int total = service.getSearchUserTotal(paramMap);

		mav.addObject("tuaccount", tuaccount);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/oms_sub05_01_detail")
	public String UserDetail(Model model, @RequestParam String user_cd) throws IOException, NumberFormatException {

		K7_USER_ACCOUNT_OMS tuaccount = service.UserDetail(user_cd);
		List<K7_USER_AUTH_OMS> userLv = service.getUserLevel();

		model.addAttribute("userLv", userLv);
		model.addAttribute("tuaccount", tuaccount);
		return "oms_sub05_01_detail";
	}

	@RequestMapping(value = "/userUpdate")
	@ResponseBody
	public void UserUpdate(Model model, HttpServletResponse response, K7_USER_ACCOUNT_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws Exception {
		if (paramMap.get("pwd").length() < 15) {
			String encryUserPwd = enc.encryData(paramMap.get("pwd"));
			uao.setPASSWORD(encryUserPwd); // 암호화 된 데이터를 객체에 넣어줌
		} else {
			uao.setPASSWORD(paramMap.get("pwd"));
		}

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		uao.setUSER_CD(Integer.parseInt(paramMap.get("user_cd")));
		uao.setUSER_LV(paramMap.get("userLv"));
		uao.setACCOUNT(paramMap.get("user_id"));
		uao.setUSER_NM(paramMap.get("user_nm"));
		uao.setPHONE(paramMap.get("phone"));
		uao.setUSER_EMAIL(paramMap.get("email"));
		uao.setUSER_ADDR(paramMap.get("addr"));
		uao.setUSER_ADDR_DETAIL(paramMap.get("addr_dt"));
		uao.setMDF_USER_CD(uSession.getUSER_CD());
		uao.setCPN_CD(paramMap.get("cpncd"));

		int userUpdate = service.UserUpdate(uao);

		if (userUpdate > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	@RequestMapping(value = "/oms_sub05_01_insert")
	public String insertPage(Model model, HttpSession session) {

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		List<K7_USER_AUTH_OMS> userLv = service.getUserLevel();

		model.addAttribute("userLv", userLv);

		return "oms_sub05_01_insert";
	}

	@RequestMapping(value = "/userCheck")
	@ResponseBody
	public ModelAndView userCheck(String user_id, ModelAndView mav) throws IOException, NumberFormatException {

		int checkId = service.getUserCheck(user_id);

		mav.addObject("userId", checkId);

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/nmCheck")
	@ResponseBody
	public ModelAndView nmCheck(String user_nm, ModelAndView mav) throws IOException, NumberFormatException {

		int checkNd = service.getNmCheck(user_nm);

		mav.addObject("userNm", checkNd);

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/userInsert")
	@ResponseBody
	public void UserInsert(Model model, HttpServletResponse response, K7_USER_ACCOUNT_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		String phone = String.join("", paramMap.get("phone").split("-"));

		uao.setUSER_LV(paramMap.get("userLv"));
		uao.setACCOUNT(paramMap.get("user_id"));
		uao.setPASSWORD(paramMap.get("pwd"));
		uao.setUSER_NM(paramMap.get("user_nm"));
		uao.setPHONE(phone);
		uao.setUSER_EMAIL(paramMap.get("email"));
		uao.setREG_USER_CD(uSession.getUSER_CD());
		uao.setUSER_ADDR(paramMap.get("addr"));
		uao.setUSER_ADDR_DETAIL(paramMap.get("addr_dt"));
		uao.setCPN_CD(paramMap.get("cpncd"));

		int userInsert = service.UserInsert(uao);

		if (userInsert > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}
}
