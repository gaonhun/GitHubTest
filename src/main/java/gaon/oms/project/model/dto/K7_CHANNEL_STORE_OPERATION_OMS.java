package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_CHANNEL_STORE_OPERATION_OMS {
	private String CH_CD; // 채널사 코드
	private String STO_CD; // 매장 코드
	private String CH_STATE; // 채널사 매장 상태
	private String CH_SEND_FLAG; // 채널사 전송 플래그
	private int REG_USER_CD; // 등록 사용자
	private Timestamp REG_DATE; // 등록일자
	private int MDF_USER_CD; // 수정 사용자
	private Timestamp MDF_DATE; // 수정일자

	private String CH_STATE_NM; // 채널사 매장 상태명
	private String CH_SEND_FLAG_NM; // 채널사 전송 플래그명

	private String REG_USER_NM; // 등록 사용자명
	private String MDF_USER_NM; // 수정 사용자명
	private String CH_NM; // 채널사명
}