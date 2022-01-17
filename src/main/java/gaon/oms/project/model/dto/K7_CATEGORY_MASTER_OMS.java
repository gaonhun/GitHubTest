package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_CATEGORY_MASTER_OMS {
	private int CTGR_CD;// *카테고리코드*
	private String CTGR_NM;// *카테고리명*
	private String CTGR_TYPE;// *카테고리타입*
	private int CTGR_LEVEL;// *카테고리차수*
	private int SEQ;// *정렬순서*
	private int FIRST_CTGR;// *첫번째카테고리*
	private int SECOND_CTGR;// *두번째카테고리*
	private int THIRD_CTGR;// *세번째카테고리*
	private int REG_USER_CD;// *등록사용자*
	private Timestamp REG_DATE;// *등록일시*
	private int MDF_USER_CD;// *수정사용자*
	private Timestamp MDF_DATE;// *수정일시*
}