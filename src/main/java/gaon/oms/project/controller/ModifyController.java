package gaon.oms.project.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.service.ModifyService;
import lombok.Setter;

@Controller
public class ModifyController {

	@Setter(onMethod_ = @Autowired)
	private ModifyService service;

	@RequestMapping(value = "/oms_sub04_01")
	public String oms_sub04_01() {
		return "oms_sub04_01";
	}

	@RequestMapping(value = "/oms_sub01_01edit")
	@ResponseBody
	public void oms_sub01_01edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		map.put("mdf_user_cd", uSession.getUSER_CD());

		try {
			service.setUpdateSub01_01detail(map);
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

	@SuppressWarnings("null")
	@RequestMapping(value = "/oms_sub01_01editArray")
	@ResponseBody
	public void oms_sub01_01editArray(@RequestParam String[][] modifyArr, HttpSession session,
			HttpServletResponse response) throws IOException {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> map = null;
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		String[] listKey = { "CH_CD", "CH_STATE", "CH_SEND_FLAG", "STO_CD" };
		try {
			for (int r = 0; r < modifyArr.length; r++) {
				map = new HashMap<String, String>();
				for (int c = 0; c < modifyArr[r].length; c++) {
					if (modifyArr[r][c].equals("temp")) {
						break;
					}

					map.put(listKey[c], modifyArr[r][c].toString());

				}
				result.add(map);
			}

			paramMap.put("chst_op", result);
			paramMap.put("MDF_USER_CD", uSession.getUSER_CD());

			service.setChannelStoreOper(paramMap);
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

	@RequestMapping(value = "/oms_sub02_01edit")
	@ResponseBody
	public void oms_sub02_01edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		map.put("mdf_user_cd", uSession.getUSER_CD());

		try {
			service.setUpdateSub02_01detail(map);
			response.getWriter().print(true);

		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

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

	@RequestMapping(value = "/oms_sub05_02edit")
	@ResponseBody
	public void oms_sub05_02edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		map.put("MDF_USER_CD", uSession.getUSER_CD());

		try {
			service.setUpdateChannelData(map);
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

	@RequestMapping(value = "/oms_sub05_03edit")
	@ResponseBody
	public void oms_sub05_03edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		map.put("MDF_USER_CD", uSession.getUSER_CD());

		try {
			service.setUpdateChannelData(map);
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
}
