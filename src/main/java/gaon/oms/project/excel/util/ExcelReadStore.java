package gaon.oms.project.excel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelReadStore {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption) throws IOException {
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		int sheetCnt = wb.getNumberOfSheets(); // 시트의 수를 가져오기 위한 변수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;

		String cellName = "";

		/**
		 * 각 row마다의 값을 저장할 맵 객체 저장되는 형식은 다음과 같다. put("A", "이름"); put("B", "게임명");
		 */
		Map<String, Object> map = null;

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		/*
		 * 각 Row를 리스트에 담는다. 하나의 Row를 하나의 Map으로 표현되며 List에는 모든 Row가 포함될 것이다.
		 */

		for (int i = 0; i < sheetCnt; i++) {
			System.out.println("Sheet 이름: " + wb.getSheetName(i));
			Sheet sheet = wb.getSheetAt(i);

			int numOfRows = sheet.getPhysicalNumberOfRows() - 1; // 유효한 데이터가 있는 행의 개수를 가져온다.

			System.out.println("유효 행 개수 ======= " + numOfRows);

			int rowExcCnt = 1000;

			int rowDivs = (int) Math.ceil((double)numOfRows / rowExcCnt);

			int rowIndex = excelReadOption.getStartRow() - 2;

			for (int r = 1; r <= rowDivs; r++) {
				// ArrayList 초기화
				result = new ArrayList<Map<String, Object>>();

				System.out.println(r + "번째 반복 시작 ========== result 초기화");

				rowIndex++;
				for (; rowIndex <= numOfRows; rowIndex++) {
					row = sheet.getRow(rowIndex);

					if (row != null) {
						/*
						 * 가져온 Row의 Cell의 개수를 구한다.
						 */
						numOfCells = row.getPhysicalNumberOfCells(); // 한개의 행마다 몇개의 cell이 있는지 체크

						/*
						 * 데이터를 담을 맵 객체 초기화
						 */
						map = new HashMap<String, Object>();

						/*
						 * cell의 수 만큼 반복한다.
						 */
						for (int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
							/*
							 * Row에서 CellIndex에 해당하는 Cell을 가져온다.
							 */
							cell = row.getCell(cellIndex);
							/*
							 * 현재 Cell의 이름을 가져온다 이름의 예 : A,B,C,D,......
							 */
							cellName = ExcelCellRef.getName(cell, cellIndex);
							/*
							 * 추출 대상 컬럼인지 확인한다 추출 대상 컬럼이 아니라면, for로 다시 올라간다
							 */
							if (!excelReadOption.getOutputColumns().contains(cellName)) {
								continue;
							}
							/*
							 * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
							 */

							if (cellName.equals("E")) {
								String bizno = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "");

								map.put(cellName, bizno);

								continue;
							}

							if (cellName.equals("H")) {
								String accnum = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "");

								map.put(cellName, accnum);

								continue;
							}

							if (cellName.equals("I")) {
								String cpntel = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "");

								map.put(cellName, cpntel);

								continue;
							}

							if (cellName.equals("K")) {
								String mnghpno = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "");

								if (StringUtils.isNotBlank(mnghpno)) {
									if (!mnghpno.substring(0, 1).equals("0")) {
										mnghpno = "0" + mnghpno;
									}
								}

								map.put(cellName, mnghpno);

								continue;
							}

							if (cellName.equals("M")) {
								String fchpno = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "");
								if (StringUtils.isNotBlank(fchpno)) {
									if (!fchpno.substring(0, 1).equals("0")) {
										fchpno = "0" + fchpno;
									}
								}
								map.put(cellName, fchpno);

								continue;
							}

							// 영업시간
							if (cellName.equals("T")) {
								String[] wdtime = ExcelCellRef.getValue(cell).replaceAll(" ", "").split("-");
								map.put("opwd", wdtime[0]);
								map.put("clwd", wdtime[1]);

								continue;
							}

							if (cellName.equals("U")) {
								String[] wntime = ExcelCellRef.getValue(cell).replaceAll(" ", "").split("-");

								map.put("opwn", wntime[0]);
								map.put("clwn", wntime[1]);

								continue;
							}

							if (cellName.equals("V")) {
								String[] hdtime = ExcelCellRef.getValue(cell).replaceAll(" ", "").split("-");

								map.put("ophd", hdtime[0]);
								map.put("clhd", hdtime[1]);

								continue;
							}

							if (cellName.equals("Z")) {
								List<String> chList = Arrays
										.asList(ExcelCellRef.getValue(cell).replaceAll(" ", "").split(","));

								map.put(cellName, chList);

								continue;
							}

							map.put(cellName, ExcelCellRef.getValue(cell));
							/*
							 * 만들어진 Map객체를 List로 넣는다.
							 */
						}
						result.add(map);
						if (rowIndex % rowExcCnt == 0) {
							break;
						}
					}
				}
				System.out.println("result " + result.size() + "개 저장완료");
				paramMap.put(Integer.toString(r), result);
				System.out.println("======== paramMap 저장 실행 ========");
			}
		}
		wb.close();
		return paramMap;
	}
}