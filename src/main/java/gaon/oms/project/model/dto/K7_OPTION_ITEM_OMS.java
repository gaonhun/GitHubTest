package gaon.oms.project.model.dto;

import lombok.Data;

@Data
public class K7_OPTION_ITEM_OMS {
	private String OPTION_ITEM_CD;// *옵션아이템코드*
	private String OPTION_CD;// *옵션코드*
	private String MENU_CD;// *메뉴코드*
	private String ITEM_CD;// *아이템코드*
	private String ITEM_NM;// *아이템명*
	private int PRICE;// *단가*
	private int SEQUENCE;// *정렬순서*
}