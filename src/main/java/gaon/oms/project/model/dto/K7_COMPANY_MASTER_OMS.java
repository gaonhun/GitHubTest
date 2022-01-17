package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_COMPANY_MASTER_OMS {
	private String CPN_CD; // 업체코드
	private String CPN_NM; // 업체명
	private String CPN_ADDR;// 주소
	private String CPN_ROAD_ADDR;// 도로명 주소
	private String CPN_ADDR_DT; // 상세주소
	private String CPN_STATE;// 업체 상태
	private String CEO_NM;// 대표자명
	private String CPN_BIZ_NO; // 사업자번호
	private String CPN_BIZ_TYPE; // 사업자유형
	private String CPN_TEL;// 매장전화
	private String HPNO;// 휴대전화
	private String EMAIL; // 이메일
	private String DAY_START_TIME; // 주중 영업시작일
	private String DAY_END_TIME;// 주중 영업종료일
	private String DLV_CH_NM; // 배송채널명
	private String EMAIL_ID; // 이메일 아이디
	private String CPN_IMAGE_URL;// 업체 이미지 URL
	private String CPN_DESC; // 업체 설명
	private String OPN_DT; // 오픈일자
	private int REG_USER_CD; // 등록사용자 코드
	private Timestamp REG_DATE; // 등록일시
	private int MDF_USER_CD; // 수정 사용자 코드
	private Timestamp MDF_DATE; // 수정일시
	private Timestamp STATE_MDF_DATE; // 상태변경일시

	// 2021-10-16 추가 컬럼
	private String K7_LINK_CD; // K7 연동 코드
	private String LINK_CD; // K7 연동 코드 (숫자제거)
	private String MEMO; // 비고
	private String MNG_NM; // 점장명
	private String MNG_HPNO; // 휴대폰번호(점장)
	private String FC_NM; // 담당 FC명
	private char IN_APP; // 인앱 여부

	// 2021-08-10 추가 컬럼
	private String ROAD_ADDR;
	private String ROAD_ADDR_DT;
	private String LOC_LAT;
	private String LOC_LNG;
	private String REG_USER_NM; // 등록사용자
	private String MDF_USER_NM; // 수정사용자

	private int RN; // 행 넘버

	// 2021-10-16 추가 컬럼
	private String ORD_CH_NM; // 연동 채널사 이름
	private String CPN_STATE_NM; // 연동 채널사 이름
}
