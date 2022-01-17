package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_ORDER_HEADER_OMS {
	private long ORDER_RE_NO;// *중계주문번호*
	private String STO_CD;// *매장코드*
	private String ORDER_CH_CD;// *채널사코드*
	private String ORDER_CH_NO;// *채널사주문번호*
	private String ORDER_TYPE;// *주문타입*
	private String ORDER_DATE;// *채널사주문일시*
	private String ORDER_STATE;// *주문상태*
	private char IS_PREPAID;// *선결제여부*
	private String PAY_TYPE;// *결제타입*
	private String DIS_TYPE_NM; // *할인타입명*
	private int TOT_AMT;// *총매출액*
	private int DIS_AMT;// *할인금액*
	private int NET_AMT;// *순매출액*
	private int DLV_AMT;// *배달금액*
	private int PAY_AMT;// *결제금액*
	private String LAND_ADDR;// *지번주소*
	private String LAND_ADDR_DT;// *지번주소상세*
	private String ROAD_ADDR;// *도로명주소*
	private String ROAD_ADDR_DT;// *도로명주소상세*
	private String LOC_LAT;// *위도*
	private String LOC_LNG;// *경도*
	private String MEM_PHONE;// *고객연락처*
	private String SAFE_MEM_PHONE;// *고객안심번호*
	private String REQ_MSG;// *요청메세지*
	private Timestamp RESERVE_DATE;// *예약일시*
	private Timestamp REG_DATE;// *등록일시*
	private Timestamp MDF_DATE;// *수정일시*

	private String STO_NM; // *매장명*
	private String ORDER_CH_NM; // *주문채널사명*
	private String DLV_CH_NM; // *배달대행사명*
	private int DLV_TIME; // *배달 대행 최소 요청 시간*
	private String DLV_STATE; // *배달 대행 상태*

	// 주문통계
	private int TOTAL_COUNT; // 전체건수
	private long TOTAL_AMT; // 주문 합계
	private int COMMIT_COUNT; // 성공 건수
	private long COMMIT_AMT;// 승인금액
	private int CANCEL_COUNT;// 취소 건수
	private long CANCEL_AMT;// 취소금액

	private int RN; // 행 넘버
}