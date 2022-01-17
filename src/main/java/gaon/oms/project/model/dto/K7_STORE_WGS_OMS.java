package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

//매장 상권 데이터
@Data
public class K7_STORE_WGS_OMS {

	private String STO_CD;// 매장코드
	private String LOC_LAT;// 위도
	private String LOC_LNG;// 경도
	private Timestamp REG_DATE;// 등록일시
}
