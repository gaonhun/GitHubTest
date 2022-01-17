package gaon.oms.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.SearchService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class SearchController {

	@Setter(onMethod_ = @Autowired)
	private SearchService service;

	// 상세 검색 팝업창
	@GetMapping("/oms_sub_search01")
	public String oms_sub_search01() {

		return "oms_sub_search01";
	}

	@PostMapping("/oms_sub_search01_process")
	@ResponseBody
	public ModelAndView oms_sub_search01_process(Criteria cri, ModelAndView mav) throws Exception {

		List<K7_COMPANY_MASTER_OMS> tbsView = service.getSearchStore(cri);

		log.info("01_process cri : " + cri);

		int total = service.getSearchStoreTotal(cri);

		mav.addObject("tbsView", tbsView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상세 검색 팝업창
	@GetMapping("/oms_sub_search02")
	public String oms_sub_search02() {

		return "oms_sub_search02";
	}

	@RequestMapping("/oms_sub_search02_process")
	@ResponseBody
	public ModelAndView oms_sub_search02_process(Criteria cri, ModelAndView mav) throws Exception {

		List<K7_COMPANY_MASTER_OMS> tocView = service.getSearchChannel(cri);

		int total = service.getSearchChannelTotal(cri);

		mav.addObject("tocView", tocView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상세 검색 팝업창
	@GetMapping("/oms_sub_search03")
	public String oms_sub_search03() {

		return "oms_sub_search03";
	}

	@RequestMapping("/oms_sub_search03_process")
	@ResponseBody
	public ModelAndView oms_sub_search03_process(Criteria cri, ModelAndView mav) throws Exception {

		List<K7_COMPANY_MASTER_OMS> tdcView = service.getSearchDelivery(cri);

		int total = service.getSearchDeliveryTotal(cri);

		mav.addObject("tdcView", tdcView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상세 검색 팝업창
	@GetMapping("/oms_sub_search04")
	public String oms_sub_search04() {

		return "oms_sub_search04";
	}

	@RequestMapping("/oms_sub_search04_process")
	@ResponseBody
	public ModelAndView oms_sub_search04_process(Criteria cri, ModelAndView mav) throws Exception {

		List<K7_MENU_MASTER_OMS> tpmView = service.getSearchProduct(cri);

		int total = service.getSearchProductTotal(cri);

		mav.addObject("tpmView", tpmView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

}
