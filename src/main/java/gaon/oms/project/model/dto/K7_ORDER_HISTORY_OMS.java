package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_ORDER_HISTORY_OMS {
	private int HISTORY_ID;// *이력 아이디*
	private long ORDER_RE_NO;// *중계주문번호*
	private String HISTORY_TYPE;// *주문이력타입*
	private String ORDER_STATE;// *주문상태*
	private String DELIVERY_STATE;// *배달 대행 상태*
	private String STATUS_CD;// *결과코드*
	private String STATUS_MSG;// *결과메세지*
	private int REG_USER_CD; // 등록사용자코드
	private String REG_USER_NM; // 등록사용자명
	private Timestamp REG_DATE;// *등록일시*
}
