package gaon.oms.project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import gaon.oms.project.excel.util.ExcelReadOption;
import gaon.oms.project.excel.util.ExcelReadProduct;
import gaon.oms.project.excel.util.ExcelReadStore;
import gaon.oms.project.model.dto.K7_COMPANY_MASTER_OMS;
import gaon.oms.project.model.dto.K7_MENU_MASTER_OMS;
import gaon.oms.project.model.dto.K7_ORDER_HEADER_OMS;
import gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS;
import gaon.oms.project.model.service.ExcelService;
import lombok.Setter;

@Controller
//엑셀 컨트롤러
public class ExcelController {

	@Setter(onMethod_ = @Autowired)
	private ExcelService service;

	// 01_01 엑셀 페이지
	@RequestMapping("/oms_sub01_01excel")
	public String oms_sub01_01excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException {

		paramMap.get("scd");
		paramMap.get("occd");
		paramMap.get("dccd");
		paramMap.get("sst");

		// 워크북 생성
		@SuppressWarnings("resource")
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 244, (byte) 244, (byte) 244 }, null));
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont headerFont = (XSSFFont) wb.createFont();
		headerFont.setFontName("Microsoft JhengHei");
		headerFont.setFontHeight((short) 200);
		headerFont.setColor(new XSSFColor(new byte[] { (byte) 113, (byte) 113, (byte) 113 }, null));
		headerFont.setBold(true);

		headStyle.setFont(headerFont);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = (XSSFCellStyle) wb.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont bodyFont = (XSSFFont) wb.createFont();
		bodyFont.setFontName("Microsoft JhengHei");
		bodyFont.setFontHeight((short) 180);
		bodyFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		bodyStyle.setFont(bodyFont);

		List<K7_COMPANY_MASTER_OMS> storeList = service.getStoreExcel(paramMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 10;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("매장리스트");

		sheet.setColumnWidth(0, 5 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 60 * 256);
		sheet.setColumnWidth(4, 12 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 15 * 256);
		sheet.setColumnWidth(7, 15 * 256);
		sheet.setColumnWidth(8, 45 * 256);
		sheet.setColumnWidth(9, 20 * 256);
		sheet.setColumnWidth(10, 25 * 256);

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("매장명");
		row.createCell(cellCount++).setCellValue("주소");
		row.createCell(cellCount++).setCellValue("매장상태");
		row.createCell(cellCount++).setCellValue("매장전화");
		row.createCell(cellCount++).setCellValue("주중영업시작");
		row.createCell(cellCount++).setCellValue("주중영업끝");
		row.createCell(cellCount++).setCellValue("채널사명");
		row.createCell(cellCount++).setCellValue("배달대행사명");
		row.createCell(cellCount++).setCellValue("등록일자");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (K7_COMPANY_MASTER_OMS cmo : storeList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(cmo.getRN());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_CD()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(cmo.getCPN_NM());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_ADDR());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_STATE());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_TEL());
			row.createCell(cellCount++).setCellValue(cmo.getDAY_START_TIME());
			row.createCell(cellCount++).setCellValue(cmo.getDAY_END_TIME());
			row.createCell(cellCount++).setCellValue(cmo.getORD_CH_NM());
			row.createCell(cellCount++).setCellValue(cmo.getDLV_CH_NM());
			row.createCell(cellCount++).setCellValue(cmo.getREG_DATE() == null ? ""
					: String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", cmo.getREG_DATE()));

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "K7_OMS_매장리스트_" + getNowDatetime + ".xlsx";

		// String filepath = "D:\\excel다운로드파일저장\\" + fileName;

		// 서버 측 다운로드
		// FileOutputStream fileOut = new FileOutputStream(filepath);
		// wb.write(fileOut);

		OutputStream os = new BufferedOutputStream(response.getOutputStream());

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		try {
			wb.write(os);
			os.flush();

			// 종료
			// fileOut.close();
			wb.dispose();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return "oms_sub01_01";
	}

	// 02_01 엑셀 페이지
	@RequestMapping("/oms_sub02_01excel")
	public String oms_sub02_01excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException, ParseException {
		paramMap.get("scd");
		paramMap.get("pcd");
		paramMap.get("etyp");
		paramMap.get("sinp");

		HashMap<String, Object> tempMap = paramMap;

		// 워크북 생성
		@SuppressWarnings("resource")
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 244, (byte) 244, (byte) 244 }, null));
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont headerFont = (XSSFFont) wb.createFont();
		headerFont.setFontName("Microsoft JhengHei");
		headerFont.setFontHeight((short) 200);
		headerFont.setColor(new XSSFColor(new byte[] { (byte) 113, (byte) 113, (byte) 113 }, null));
		headerFont.setBold(true);

		headStyle.setFont(headerFont);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = (XSSFCellStyle) wb.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont bodyFont = (XSSFFont) wb.createFont();
		bodyFont.setFontName("Microsoft JhengHei");
		bodyFont.setFontHeight((short) 180);
		bodyFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		bodyStyle.setFont(bodyFont);

		// 숫자 포멧
		XSSFCellStyle numStyle = (XSSFCellStyle) wb.createCellStyle();

		numStyle.setBorderBottom(BorderStyle.THIN);
		numStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderRight(BorderStyle.THIN);
		numStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderTop(BorderStyle.THIN);
		numStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderLeft(BorderStyle.THIN);
		numStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		XSSFFont numFont = (XSSFFont) wb.createFont();
		numFont.setFontName("Microsoft JhengHei");
		numFont.setFontHeight((short) 180);
		numFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		numStyle.setFont(numFont);

		List<K7_MENU_MASTER_OMS> productList = service.getProductExcel(tempMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 6;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("메뉴리스트");

		// 총 240정도가 적당
		sheet.setColumnWidth(0, 5 * 256);// 순번
		sheet.setColumnWidth(1, 20 * 256);// 메뉴코드
		sheet.setColumnWidth(2, 40 * 256);// 메뉴명
		sheet.setColumnWidth(3, 15 * 256);// 단가(원)
		sheet.setColumnWidth(4, 15 * 256);// 메뉴상태
		// sheet.setColumnWidth(4, 30 * 256);// 상태수정일시
		sheet.setColumnWidth(5, 35 * 256);// 매장 내 메뉴 분류
		// sheet.setColumnWidth(6, 15 * 256);// 등록자명
		sheet.setColumnWidth(6, 25 * 256);// 등록일시
		// sheet.setColumnWidth(8, 15 * 256);// 수정자명
		// sheet.setColumnWidth(9, 25 * 256);// 수정일시

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("메뉴코드");
		row.createCell(cellCount++).setCellValue("메뉴명");
		row.createCell(cellCount++).setCellValue("단가(원)");
		row.createCell(cellCount++).setCellValue("메뉴상태");
		// row.createCell(cellCount++).setCellValue("상태수정일시");
		row.createCell(cellCount++).setCellValue("매장 내 메뉴 분류");
		// row.createCell(cellCount++).setCellValue("등록자명");
		row.createCell(cellCount++).setCellValue("등록일시");
		// row.createCell(cellCount++).setCellValue("수정자명");
		// row.createCell(cellCount++).setCellValue("수정일시");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (K7_MENU_MASTER_OMS tpm : productList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(tpm.getRN()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(tpm.getMENU_CD()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(tpm.getMENU_NM());
			row.createCell(cellCount++).setCellValue(tpm.getPRICE());
			row.createCell(cellCount++).setCellValue(tpm.getMENU_STATE());
//			row.createCell(cellCount++).setCellValue(tpm.getMENU_STATE_MDF_DATE() == null ? ""
//					: String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getMENU_STATE_MDF_DATE()));
			row.createCell(cellCount++).setCellValue(tpm.getCTGR_NM());
//			row.createCell(cellCount++).setCellValue(tpm.getREG_USER_NM());
			row.createCell(cellCount++)
					.setCellValue(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getREG_DATE()));
//			row.createCell(cellCount++).setCellValue(tpm.getMDF_USER_NM());
//			row.createCell(cellCount++).setCellValue(tpm.getMDF_DATE() == null ? ""
//					: String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getMDF_DATE()));
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 3) {
					row.getCell(e).setCellStyle(numStyle);
					continue;
				}
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "K7_OMS_메뉴리스트_" + getNowDatetime + ".xlsx";

		OutputStream os = new BufferedOutputStream(response.getOutputStream());

		// 클라이언트 측 다운로드
		response.reset();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		try {
			wb.write(os);
			os.flush();

			// 종료
			// fileOut.close();
			wb.dispose();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return "oms_sub02_01";
	}

	// 03_01 엑셀 페이지
	@RequestMapping("/oms_sub03_01excel")
	public String oms_sub03_01excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException, ParseException {

		paramMap.get("scd");
		paramMap.get("ono");
		paramMap.get("chono"); // 채널사 주문 번호
		paramMap.get("occd");
		paramMap.get("sdate");
		paramMap.get("edate");

		HashMap<String, Object> tempMap = paramMap;

		// 워크북 생성
		@SuppressWarnings("resource")
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 244, (byte) 244, (byte) 244 }, null));
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont headerFont = (XSSFFont) wb.createFont();
		headerFont.setFontName("Microsoft JhengHei");
		headerFont.setFontHeight((short) 200);
		headerFont.setColor(new XSSFColor(new byte[] { (byte) 113, (byte) 113, (byte) 113 }, null));
		headerFont.setBold(true);

		headStyle.setFont(headerFont);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = (XSSFCellStyle) wb.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont bodyFont = (XSSFFont) wb.createFont();
		bodyFont.setFontName("Microsoft JhengHei");
		bodyFont.setFontHeight((short) 180);
		bodyFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		bodyStyle.setFont(bodyFont);

		// 숫자 포멧
		XSSFCellStyle numStyle = (XSSFCellStyle) wb.createCellStyle();

		numStyle.setBorderBottom(BorderStyle.THIN);
		numStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderRight(BorderStyle.THIN);
		numStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderTop(BorderStyle.THIN);
		numStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderLeft(BorderStyle.THIN);
		numStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		XSSFFont numFont = (XSSFFont) wb.createFont();
		numFont.setFontName("Microsoft JhengHei");
		numFont.setFontHeight((short) 180);
		numFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		numStyle.setFont(numFont);

		List<K7_ORDER_HEADER_OMS> orderList = service.getOrderExcel(tempMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 17;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("주문리스트");

		sheet.setColumnWidth(0, 5 * 256);// 순번
		sheet.setColumnWidth(1, 25 * 256);// 중계주문번호
		sheet.setColumnWidth(2, 25 * 256);// 채널사주문번호
		sheet.setColumnWidth(3, 15 * 256);// 매장코드
		sheet.setColumnWidth(4, 25 * 256);// 매장명
		sheet.setColumnWidth(5, 25 * 256);// 채널사명
		sheet.setColumnWidth(6, 15 * 256);// 주문상태
		sheet.setColumnWidth(7, 15 * 256);// 주문타입
		sheet.setColumnWidth(8, 20 * 256);// 상품합계금액(A)
		sheet.setColumnWidth(9, 20 * 256);// 배달비(B)
		sheet.setColumnWidth(10, 20 * 256);// 할인금액(C)
		sheet.setColumnWidth(11, 20 * 256);// 총매출액(A+B)
		sheet.setColumnWidth(12, 20 * 256);// 순매출액(A+B-C)
		sheet.setColumnWidth(13, 40 * 256);// 배달주소지
		sheet.setColumnWidth(14, 20 * 256);// 고객전화번호
		sheet.setColumnWidth(15, 20 * 256);// 배달대행사명
		sheet.setColumnWidth(16, 15 * 256);// 배달상태
		sheet.setColumnWidth(17, 25 * 256);// 등록일시

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("중계주문번호");
		row.createCell(cellCount++).setCellValue("채널사주문번호");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("매장명");
		row.createCell(cellCount++).setCellValue("채널사명");
		row.createCell(cellCount++).setCellValue("주문상태");
		row.createCell(cellCount++).setCellValue("주문타입");
		row.createCell(cellCount++).setCellValue("카드승인금액(A)");
		row.createCell(cellCount++).setCellValue("배달비(B)");
		row.createCell(cellCount++).setCellValue("할인금액(C)");
		row.createCell(cellCount++).setCellValue("총매출액(A+B)");
		row.createCell(cellCount++).setCellValue("순매출액(A+B-C)");
		row.createCell(cellCount++).setCellValue("배달주소지");
		row.createCell(cellCount++).setCellValue("고객전화번호");
		row.createCell(cellCount++).setCellValue("배달대행사명");
		row.createCell(cellCount++).setCellValue("배달상태");
		row.createCell(cellCount++).setCellValue("등록일시");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (K7_ORDER_HEADER_OMS tos : orderList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(tos.getRN()); // 순번
			row.createCell(cellCount++).setCellValue(Long.toString(tos.getORDER_RE_NO())); // 중계주문번호
			row.createCell(cellCount++).setCellValue(tos.getORDER_CH_NO()); // 채널사주문번호
			row.createCell(cellCount++).setCellValue(tos.getSTO_CD()); // 매장코드
			row.createCell(cellCount++).setCellValue(tos.getSTO_NM()); // 매장명
			row.createCell(cellCount++).setCellValue(tos.getORDER_CH_NM()); // 채널사명
			row.createCell(cellCount++).setCellValue(tos.getORDER_STATE()); // 주문상태
			row.createCell(cellCount++).setCellValue(tos.getORDER_TYPE()); // 주문타입
			row.createCell(cellCount++).setCellValue(tos.getPAY_AMT()); // 상품합계금액
			row.createCell(cellCount++).setCellValue(tos.getDLV_AMT()); // 배달비(원)
			row.createCell(cellCount++).setCellValue(tos.getDIS_AMT()); // 할인금액(원)
			row.createCell(cellCount++).setCellValue(tos.getTOT_AMT()); // 총매출액(원)
			row.createCell(cellCount++).setCellValue(tos.getNET_AMT()); // 순매출액(원)
			row.createCell(cellCount++).setCellValue(tos.getLAND_ADDR() + ", " + tos.getLAND_ADDR_DT()); // 배달지주소
			row.createCell(cellCount++).setCellValue(phoneCheck(tos.getMEM_PHONE(), tos.getSAFE_MEM_PHONE())); // 고객전화번호
			row.createCell(cellCount++).setCellValue(tos.getDLV_CH_NM()); // 배달사명
			row.createCell(cellCount++).setCellValue(tos.getDLV_STATE()); // 배달상태
			row.createCell(cellCount++)
					.setCellValue(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tos.getREG_DATE())); // 등록일시
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 8 || e == 9 || e == 10 || e == 11 || e == 12) {
					row.getCell(e).setCellStyle(numStyle);
					continue;
				}

				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "K7_OMS_주문리스트_" + getNowDatetime + ".xlsx";

		OutputStream os = new BufferedOutputStream(response.getOutputStream());

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		try {
			wb.write(os);
			os.flush();

			// 종료
			// fileOut.close();
			wb.dispose();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return "oms_sub03_01";
	}

	@RequestMapping(value = "/storeUpload")
	@ResponseBody
	public void StoreUpload(MultipartHttpServletRequest request, HashMap<String, Object> paramMap, HttpSession session,
			HttpServletResponse response) {

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/" + month + "/" + day + "/StoreUpload/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = new HashMap<String, Object>();
		List<Map<String, Object>> arrList = new ArrayList<Map<String, Object>>();
		try {
			excelRead = ExcelReadStore.read(excelReadOption);

		} catch (IOException e2) {
			System.out.println("ExcelReadStore IOException ========== " + e2);
		}
	
		paramMap.put("usercd", uSession.getUSER_CD());

		// Insert 보내기
		try {
			for (int i = 1; i <= excelRead.size(); i++) {
				arrList = (List<Map<String, Object>>) excelRead.get(Integer.toString(i));
				paramMap.put("excel", arrList);
				System.out.println(i+"번째 업로드 진행 ======");
				service.setStoreAllUpload(paramMap);
			}
			response.getWriter().print(excelFile.getOriginalFilename());
			System.out.println("======= 업로드 완료 =======");
		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("STORE UPLOAD ERROR ===============" + e.getMessage());
			try {
				if (matcher.find()) {
					String errCd = matcher.group(1).trim();
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.out.println("errCd ================ " + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.out.println("MATCH외 오류 =========== " + e.getStackTrace());
				}
				
			}catch (IOException e1) {
				System.out.println("response IOException ======= " + e1);
			}
		}
	}

	@RequestMapping(value = "/productUpload")
	@ResponseBody
	public void ProductUpload(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");
		paramMap.put("usercd", uSession.getUSER_CD());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/" + month + "/" + day + "/MenuUpload/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = new HashMap<String, Object>();
		List<Map<String, Object>> arrList = new ArrayList<Map<String, Object>>();
		try {
			excelRead = ExcelReadProduct.read(excelReadOption);
		} catch (IOException e1) {
			System.out.println("ExcelReadStore IOException ==== " + e1.getStackTrace());
		}

		// Insert 보내기
		try {
			service.deleteAllMenu();
			for (int i = 1; i <= excelRead.size(); i++) {
				arrList = (List<Map<String, Object>>) excelRead.get(Integer.toString(i));
				paramMap.put("menu", arrList);
				System.out.println(i+"번째 업로드 진행 ======");
				service.setAllProductUpload(paramMap);
			}
			service.setMenuUpdate_GDC();
			
			response.getWriter().print(excelFile.getOriginalFilename());
			System.out.println("======= 업로드 완료 =======");
		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
//			final Pattern pattern2 = Pattern.compile("ORA-06512(.+?)행", Pattern.DOTALL);
//			final Matcher matcher2 = pattern2.matcher(ExceptionUtils.getMessage(e));

			System.out.println("MENU UPLOAD ERROR ===============" + e.getMessage());
			try {
				if (matcher.find()) {
					String errCd = matcher.group(1).trim();
					response.setStatus(400);
						response.getWriter().write(errCd);
					System.out.println("errCd ================ " + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ====== " + e1.getStackTrace());
			}
		}
	}

	@RequestMapping(value = "/productPartUpload")
	@ResponseBody
	public void ProductPartUpload(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {

		K7_USER_ACCOUNT_OMS uSession = (K7_USER_ACCOUNT_OMS) session.getAttribute("UAccount");
		paramMap.put("usercd", uSession.getUSER_CD());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/" + month + "/" + day + "/MenuPartUpload/";

		System.out.println(path);
		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = new HashMap<String, Object>();
		List<Map<String, Object>> arrList = new ArrayList<Map<String, Object>>();
		try {
			excelRead = ExcelReadProduct.read(excelReadOption);
		} catch (IOException e2) {
			System.out.println("ExcelReadStore IOException ==== " + e2.getStackTrace());
		}

		// Insert 보내기
		try {
			for (int i = 1; i <= excelRead.size(); i++) {
				arrList = (List<Map<String, Object>>) excelRead.get(Integer.toString(i));
				paramMap.put("menu", arrList);
				System.out.println(i+"번째 업로드 진행 ======");
				service.setPartProductUpload(paramMap);
			}
			response.getWriter().print(excelFile.getOriginalFilename());
			System.out.println("======= 업로드 완료 =======");
		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("MENU PART UPLOAD ERROR ===============" + e.getMessage());
			try {
				if (matcher.find()) {
					String errCd = matcher.group(1).trim();
					response.setStatus(400);
						response.getWriter().write(errCd);
					System.out.println("errCd ================ " + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ====== " + e1.getStackTrace());
			}
		}
	}
	
	@RequestMapping(value = "/fileUpload")
	@ResponseBody
	public void fileUpload(MultipartHttpServletRequest request, HashMap<String, Object> paramMap, HttpSession session,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/" + month + "/" + day + "/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		response.getWriter().print(excelFile.getOriginalFilename());

	}

	@RequestMapping(value = "/SampleDown")
	public void SampleDownload(@RequestParam String stat, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		try {
			String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

			if (stat.equals("Store")) {
				path += "K7_OMS_매장_업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
			} else if (stat.equals("Menu")) {
				path += "K7_OMS_메뉴_업로드_양식.xlsx";
			} else {
				path += "Gaon.war";
			}

			
			File file = new File(path);
			String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																							// 알려주는 헤더

			@SuppressWarnings("resource")
			FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
			OutputStream out = response.getOutputStream();

			int read = 0;
			byte[] buffer = new byte[1024];
			while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																	// 파일이 없음
				out.write(buffer, 0, read);
			}

		} catch (Exception e) {
			throw new Exception("download error");
		}
	}

	public String phoneCheck(String basic, String safty) {
		String phoneNum = null;
		if (StringUtils.isNotBlank(basic)) {
			phoneNum = basic;
		} else if (StringUtils.isNotBlank(safty)) {
			phoneNum = safty;
		}
		return phoneNum;
	}
}