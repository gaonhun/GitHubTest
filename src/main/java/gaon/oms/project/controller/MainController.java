package gaon.oms.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import gaon.oms.project.model.service.MainService;
import lombok.Setter;

@Controller
// 메인 컨트롤러
public class MainController {

	@Setter(onMethod_ = @Autowired)
	private MainService service;

	@RequestMapping(value = "/")
	public String indexPage(HttpSession session) {
		session.invalidate();
		return "oms_index";
	}

	// 네비게이션 바
	@RequestMapping("/oms_subnav")
	public ModelAndView oms_subnav(ModelAndView mav) {

		mav.setViewName("oms_subnav");

		return mav;
	}

	// OMS_MAIN 페이지
	@RequestMapping(value = "/oms_main")
	public ModelAndView oms_main(ModelAndView mav, HttpSession session) throws IOException {
		// 금일 리스트
		List<K7_ORDER_HEADER_OMS> todayList = service.selectTodayCountList();
		// 전일 리스트
		List<K7_ORDER_HEADER_OMS> yesList = service.selectYesCountList();

		// 채널사 리스트
		List<K7_COMPANY_MASTER_OMS> franchList = service.selectFranchiseNumber();
		// 배달 대행 리스트
		List<K7_COMPANY_MASTER_OMS> deliveryList = service.selectDeliveryNumber();

		mav.addObject("todayList", todayList);
		mav.addObject("yesList", yesList);

		session.setAttribute("franchList", franchList);
		session.setAttribute("deliveryList", deliveryList);

		mav.setViewName("oms_main");

		return mav;
	}
}
