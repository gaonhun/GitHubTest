package gaon.oms.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.common.UrlNotFoundException;
import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_CATEGORY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_OPTION_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_STORE_MENU_OMS;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.ProductService;
import lombok.Setter;

@Controller
// 상품 관리 컨트롤러
public class ProductController {

	@Setter(onMethod_ = @Autowired)
	private ProductService service;

	// 상품 관리 메인 페이지
	@RequestMapping("/oms_sub02_01")
	public String oms_sub02_01(Model model, HttpServletRequest request, HttpSession session) {
		
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");
		
		if(uSession.getUSER_LV().equals("C")) {
			throw new UrlNotFoundException();
		}

		// 행사 타입 가져오기
		List<K7_REFERENCE_CODE_OMS> plcode = service.GetPolicyCode();

		// 매장 내 상품 분류 가져오기
		List<K7_CATEGORY_MASTER_OMS> category = service.GetCategory();

		model.addAttribute("plcode", plcode);
		model.addAttribute("category", category);

		return "/oms_sub02_01";
	}

	// AJAX 검색 처리 컨트롤러
	@RequestMapping("/oms_sub02_01_process")
	@ResponseBody
	public ModelAndView productSearch(String scd, String pcd, String etyp, String sinp, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("scd", scd); // 매장 코드
		paramMap.put("pcd", pcd); // 상품 코드
		paramMap.put("etyp", etyp); // 행사 타입
		paramMap.put("sinp", sinp); // 매장 내 상품 분류
		paramMap.put("cri", cri); // 페이징

		// 상품 리스트 검색 메소드
		List<K7_MENU_MASTER_OMS> productList = service.productSearch(paramMap);

		int total = service.getSearchProductTotal(paramMap);

		mav.addObject("productList", productList);

		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상품 리스트 상세
	@RequestMapping("/oms_sub02_01_detail")
	public String sub02_01_detail(@RequestParam String plucd, @RequestParam String scd, Model model,
			@RequestParam Map<String, Object> paramMap) {

		paramMap.put("scd", scd); // 매장 코드
		paramMap.put("plucd", plucd); // 상품 코드

		// 행사 타입 가져오기
		List<K7_REFERENCE_CODE_OMS> plcode = service.GetPolicyCode();
		// 매장 내 상품 분류 가져오기
		List<K7_CATEGORY_MASTER_OMS> category = service.GetCategory();
		// 상품 기본 정보 가져오기
		K7_MENU_MASTER_OMS pmdView = service.getProductMasterDetailData(plucd);
		// 행사 상품 옵션 정보 가져오기
		List<K7_MENU_OPTION_OMS> podView = service.getProductOptionDetailData(plucd);
		// 매장 재고 정보 가져오기
		K7_STORE_MENU_OMS tspView = service.getStoreProductDetailData(paramMap);

		model.addAttribute("plcode", plcode);
		model.addAttribute("category", category);
		model.addAttribute("pmdView", pmdView);
		model.addAttribute("podView", podView);
		model.addAttribute("tspView", tspView);

		return "oms_sub02_01_detail";
	}

	// 상세 페이지 행사 상품 옵션 정보의 Item 값 가져오기
	@PostMapping(value = "/getItemCode_process")
	@ResponseBody
	public List<K7_MENU_OPTION_OMS> getItemCode(String opcd) {

		// 상품 옵션 코드로 아이템 내역 가져오기
		List<K7_MENU_OPTION_OMS> tpoItem = service.getProductItemData(opcd);

		return tpoItem;
	}
}
