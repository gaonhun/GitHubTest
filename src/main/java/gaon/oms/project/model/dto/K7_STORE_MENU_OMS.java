package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

//매장별 상품 상태값 입력
@Data
public class K7_STORE_MENU_OMS {

	private String STO_CD;// *매장코드
	private String MENU_CD;// *상품코드*
	private int STO_STOCK_QTY;// *매장재고*
	private Timestamp STOCK_MDF_DATE;// *재고변경일시*
}
