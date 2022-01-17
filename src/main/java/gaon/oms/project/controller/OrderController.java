package gaon.oms.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParser;

import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HISTORY_OMS;
import gaon.oms.project.model.dto.K7_ORDER_MENU_OMS;
import gaon.oms.project.model.dto.K7_ORDER_OPTION_ITEM_OMS;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.OrderService;
import lombok.Setter;

@Controller
//주문 내역 컨트롤러
public class OrderController {

	@Setter(onMethod_ = @Autowired)
	private OrderService service;

	// 주문 내역 관리 메인 페이지
	@RequestMapping("/oms_sub03_01")
	public String oms_sub03_01() {
		return "/oms_sub03_01";
	}

	// AJAX 검색 처리 컨트롤러
	@RequestMapping("/oms_sub03_01_process")
	@ResponseBody
	public ModelAndView orderSearch(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.get("scd"); // 매장 코드
		paramMap.get("occd"); // 채널사 코드
		paramMap.get("ono"); // 주문 번호
		paramMap.get("chono"); // 채널사 주문 번호
		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일
		paramMap.put("cri", cri); // 페이징

		System.out.println("시작일 ===== " + paramMap.get("sdate"));
		System.out.println("종료일 ===== " + paramMap.get("edate"));
		
		// 주문 내역 검색 메소드
		List<K7_ORDER_HEADER_OMS> orderList = service.orderSearch(paramMap);

		int total = service.getSearchOrderTotal(paramMap);

		mav.addObject("orderList", orderList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 주문 내역 상세
	@RequestMapping("/oms_sub03_01_detail")
	public String orderDetailView(Model model, @RequestParam long oid, K7_ORDER_HEADER_OMS tState,
			@RequestParam String scd, @RequestParam String occd) throws IOException, NumberFormatException {

		tState.setORDER_RE_NO(oid);
		tState.setSTO_CD(scd);
		tState.setORDER_CH_CD(occd);

		// 주문 상세 정보
		K7_ORDER_HEADER_OMS orderDetail = service.orderDetail(tState);

		// 주문 메뉴 정보
		List<K7_ORDER_MENU_OMS> orderMenu = service.orderMenu(tState);

		// 주문 이력 정보
		List<K7_ORDER_HISTORY_OMS> orderHistory = service.orderHistory(tState);

		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("orderMenu", orderMenu);
		model.addAttribute("orderHistory", orderHistory);

		return "oms_sub03_01_detail";
	}

	// 주문 메뉴 Item 값 가져오기
	@PostMapping(value = "/getMenuItem_process")
	@ResponseBody
	public List<K7_ORDER_OPTION_ITEM_OMS> getOrderMenuItem(@RequestParam Map<String, Object> paramMap) {

		paramMap.get("ORDER_RE_NO"); // 중계주문번호
		paramMap.get("MENU_CD"); // 메뉴코드
		paramMap.get("MENU_CD_SEQ"); // 메뉴시퀀스

		// 상품 옵션 코드로 아이템 내역 가져오기
		List<K7_ORDER_OPTION_ITEM_OMS> ooiItem = service.getOrderMenuItem(paramMap);

		return ooiItem;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/cancel_submit")
	@ResponseBody
	public void cancel_submit(@RequestParam Map<String, Object> map, HttpSession session, HttpServletResponse response)
			throws IOException, NumberFormatException, ParseException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		String STO_CD = (String) map.get("STO_CD");
		String ORDER_CH_CD = (String) map.get("ORDER_CH_CD");
		String ORDER_CH_NO = (String) map.get("ORDER_CH_NO");
		String ORDER_RE_NO = (String) map.get("ORDER_RE_NO");
		String STATUS_MSG = (String) map.get("STATUS_MSG");
		String REG_USER_CD = Integer.toString(uSession.getUSER_CD());

		
		URL url;
		if(ORDER_CH_CD.equals("G03")) {
			url = new URL("http://10.222.161.155:9080/k7/middle/order/callcenter_cancel");
		}else {
			url = new URL("http://10.222.161.155:9080/k7/relay/order/callcenter_cancel");			
		}

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);

		conn.setRequestMethod("POST"); // 보내는 타입
		conn.setRequestProperty("Content-Type", "application/json");
		//conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");


		// 데이터
		JSONObject callcancel = new JSONObject();
		callcancel.put("STO_CD", STO_CD);
		callcancel.put("ORDER_CH_CD", ORDER_CH_CD);
		callcancel.put("ORDER_CH_NO", ORDER_CH_NO);
		callcancel.put("ORDER_RE_NO", ORDER_RE_NO);
		callcancel.put("STATUS_MSG", STATUS_MSG);
		callcancel.put("REG_USER_CD", REG_USER_CD);

		String data = callcancel.toJSONString();

		System.out.println("주문취소 전송건 ======= " + callcancel);
		
		// 전송
		OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

		try {
			osw.write(data);
			osw.flush();

			// 응답
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;

			if ((line = br.readLine()) != null) {
				System.out.println("콜센터취소 응답 전문 ===== " + line);
				JSONParser jsonPar = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonPar.parse(line);

				JSONObject outputJsonObj = new JSONObject();
				// outputJsonObj.put("STATUS_CD", jsonObj.get("STATUS_CD").toString());
				outputJsonObj.put("STATUS_MSG", jsonObj.get("STATUS_MSG").toString());

				String outputString = outputJsonObj.toJSONString();

				System.out.println("JSON변환 응답값 =========== " + outputString);
				System.out.println("STATUS_MSG 타입 확인 ========= " + jsonObj.get("STATUS_CD").toString().getClass().getName());
				if (jsonObj.get("STATUS_CD").toString().equals("200")) {
					System.out.println("IF문 TRUE 탄 주문취소 ========= " + jsonObj.get("STATUS_CD").toString());
					response.getWriter().print(outputString);
				} else {
					response.setStatus(400);
					System.out.println("IF문 ELSE 탄 주문취소 ========= " + jsonObj.get("STATUS_CD").toString());
					response.getWriter().print(jsonObj.get("STATUS_MSG").toString());
				}

			} else {
				response.setStatus(400);
				response.getWriter().print("응답이 없습니다.");
			}

			// 닫기
			osw.close();
			br.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (ProtocolException e) {

			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
