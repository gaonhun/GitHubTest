package gaon.oms.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gaon.oms.project.common.UrlNotFoundException;
import gaon.oms.project.model.dto.Criteria;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_REFERENCE_CODE_OMS;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.dto.PagingDTO;
import gaon.oms.project.model.service.ChannelService;
import lombok.Setter;

@Controller
public class ChannelController {
	@Setter(onMethod_ = @Autowired)
	private ChannelService service;

	@RequestMapping("/oms_sub05_02")
	public String oms_sub05_02(Model model, HttpSession session) {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");
		
		if(!uSession.getUSER_LV().equals("M") && !uSession.getUSER_LV().equals("A")) {
			throw new UrlNotFoundException();
		}
		
		List<K7_REFERENCE_CODE_OMS> stcode = service.GetCPNState();

		model.addAttribute("stcode", stcode);

		return "/oms_sub05_02";
	}

	@RequestMapping("/oms_sub05_02_process")
	@ResponseBody
	public ModelAndView OrderChannelSearch(String cpncd, String sst, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("cpntype", "Order");
		paramMap.put("cpncd", cpncd);
		paramMap.put("sst", sst);
		paramMap.put("cri", cri);

		List<K7_COMPANY_MASTER_OMS> ordChList = service.ChannelSearch(paramMap);

		int total = service.getOrderChannelTotal(paramMap);

		mav.addObject("ordChList", ordChList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/oms_sub05_03")
	public String oms_sub05_03(Model model, HttpSession session) {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");
		
		if(!uSession.getUSER_LV().equals("M") && !uSession.getUSER_LV().equals("A")) {
			throw new UrlNotFoundException();
		}
		
		List<K7_REFERENCE_CODE_OMS> stcode = service.GetCPNState();

		model.addAttribute("stcode", stcode);

		return "/oms_sub05_03";
	}

	@RequestMapping("/oms_sub05_03_process")
	@ResponseBody
	public ModelAndView DeliverySearch(String cpncd, String sst, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("cpntype", "Delivery");
		paramMap.put("cpn", cpncd);
		paramMap.put("sst", sst);
		paramMap.put("cri", cri);

		List<K7_COMPANY_MASTER_OMS> dvlList = service.ChannelSearch(paramMap);

		int total = service.getOrderChannelTotal(paramMap);

		mav.addObject("dvlList", dvlList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/channel_register")
	@ResponseBody
	public void ChannelRegister(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		map.put("REG_USER_CD", uSession.getUSER_CD());

		try {
			service.setInsetChannelData(map);
			response.getWriter().print(true);

		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("ERROR = = = = = = = " + e.getMessage());

			if (matcher.find()) {
				String errCd = matcher.group(1).trim();
				response.setStatus(400);
				response.getWriter().write(errCd);
			} else {
				response.setStatus(400);
				response.getWriter().write("1");
			}
		}
	}

	@RequestMapping(value = "/oms_sub05_02_detail")
	public String oms_sub05_02_detail(Model model, @RequestParam String cpncd)
			throws IOException, NumberFormatException {

		List<K7_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
		K7_COMPANY_MASTER_OMS cmo = service.getCPN_Detail(cpncd);

		model.addAttribute("stcode", stcode);
		model.addAttribute("order", cmo);

		return "oms_sub05_02_detail";
	}

	@RequestMapping(value = "/oms_sub05_03_detail")
	public String oms_sub05_03_detail(Model model, @RequestParam String cpncd)
			throws IOException, NumberFormatException {

		List<K7_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
		K7_COMPANY_MASTER_OMS cmo = service.getCPN_Detail(cpncd);

		model.addAttribute("stcode", stcode);
		model.addAttribute("delivery", cmo);

		return "oms_sub05_03_detail";
	}
}
