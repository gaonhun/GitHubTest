package gaon.oms.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.common.UrlNotFoundException;
import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_CHANNEL_STORE_OPERATION_OMS;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_OPERATION_OMS;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.StoreService;
import lombok.Setter;

@Controller
public class StoreController {

	@Setter(onMethod_ = @Autowired)
	private StoreService service;

	@RequestMapping("/oms_sub01_01")
	public String oms_sub01_01(Model model, HttpSession session) {

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		if (uSession.getUSER_LV().equals("C")) {
			throw new UrlNotFoundException();
		}

		List<K7_REFERENCE_CODE_OMS> stcode = service.GetStoreState();

		model.addAttribute("stcode", stcode);

		return "/oms_sub01_01";
	}

	@PostMapping("/oms_sub01_01_process")
	@ResponseBody
	public ModelAndView storeSearch(String scd, String occd, String dccd, String sst, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("scd", scd);
		paramMap.put("occd", occd);
		paramMap.put("dccd", dccd);
		paramMap.put("sst", sst);
		paramMap.put("cri", cri);

		List<K7_COMPANY_MASTER_OMS> storeList = service.storeSearch(paramMap);

		int total = service.getSearchStoreTotal(paramMap);

		mav.addObject("storeList", storeList);
		System.out.println(storeList);

		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/oms_sub01_01_detail")
	public String oms_sub01_01_detail(@RequestParam String stocd, Model model) {

		// 매장 상태
		List<K7_REFERENCE_CODE_OMS> stcode = service.GetStoreState();
		// 매장 기본 정보
		K7_COMPANY_MASTER_OMS sdView = service.getStoreDetailData(stocd);
		// 매장 운영 정보
		K7_STORE_OPERATION_OMS sodView = service.getStoreOperationDetailData(stocd);
		// 매장 상권 정보
		List<K7_CHANNEL_STORE_OPERATION_OMS> csoView = service.getChannelStoreOperation(stocd);
		// 채널사 전송 플래그
		List<K7_REFERENCE_CODE_OMS> chflag = service.GetChannelFlag();
		// 배달대행사정보
		List<K7_COMPANY_MASTER_OMS> dvlList = service.GetDeliveryList();

		model.addAttribute("stcode", stcode);
		model.addAttribute("sdView", sdView);
		model.addAttribute("sodView", sodView);
		model.addAttribute("csoView", csoView);
		model.addAttribute("chflag", chflag);
		model.addAttribute("dvlList", dvlList);

		return "oms_sub01_01_detail";
	}
}
