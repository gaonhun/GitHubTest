package gaon.oms.project.model.dto;

import lombok.Data;

@Data
public class K7_STORE_OPERATION_OMS {

	private String STO_CD;// *매장코드*
	private String LOC_LAT;// *위도*
	private String LOC_LONG;// *경도*
	private String DAY_START_TIME;// *주중 영업 시작*
	private String DAY_END_TIME;// *주중 영업 끝*
	private int DELIVERY_PRICE;// *배달비(원)*
	private int MAXIMUM_ORDER_PRICE;// *최대 주문 가능 금액(원)*
	private int MINIMUM_ORDER_PRICE;// *최소 주문 가능 금액(원)*
	private String WEEKEND_START_TIME;// *주말 영업 시작*
	private String WEEKEND_END_TIME;// *주말 영업 끝*
	private String HOLIDAY_START_TIME;// *공휴일 영업 시작*
	private String HOLIDAY_END_TIME;// *공휴일 영업 끝*
	private int ESTIMATED_DELIVERY_TIME;// *배달예상시간*
	private String DELIVERY_CH_CD;// *배달대행사*
	private String ACC_HOLDER;// *예금주*
	private String BANK_NM;// *은행명*
	private String ACC_NUM;// *계좌번호*
	private String STOP_START_DAY;// *운영휴일시작일*
	private String STOP_END_DAY;// *운영휴일종료일*
	private String BREAK_TIME;// *쉬는시간*
	
	private String DELIVERY_CH_NM; // 배달대행사명
}
