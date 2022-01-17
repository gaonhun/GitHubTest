package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_USER_ACCOUNT_OMS {
	private int USER_CD;
	private String USER_NM;
	private String ACCOUNT;
	private String PASSWORD;
	private String USER_ADDR;
	private String USER_ADDR_DETAIL;
	private String USER_EMAIL;
	private String PHONE;
	private String USER_LV;
	private int REG_USER_CD;
	private Timestamp REG_DATE;
	private int MDF_USER_CD;
	private Timestamp MDF_DATE;
	private String AUTH_NO;
	private String CPN_CD;

	private String LEVEL_EXP;
	private String REG_USER_NM;
	private String MDF_USER_NM;

	private int RN; // 행 넘버
}