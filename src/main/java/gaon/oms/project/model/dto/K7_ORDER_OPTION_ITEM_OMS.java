package gaon.oms.project.model.dto;

import lombok.Data;

@Data
public class K7_ORDER_OPTION_ITEM_OMS {
	private long ORDER_RE_NO; // *중계주문번호*
	private String MENU_CD;// *메뉴코드*
	private String ITEM_CD;// *아이템코드*
	private String ITEM_NM;// *아이템명*
	private int QTY;// *수량*
	private int PRICE;// *단가*
	private int AMOUNT;// *금액*
	private int MENU_CD_SEQ;// *메뉴코드순번*
	private int ITEM_CD_SEQ;// *아이템코드순번*
}
