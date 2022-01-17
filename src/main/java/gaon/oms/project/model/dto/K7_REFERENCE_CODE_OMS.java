package gaon.oms.project.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class K7_REFERENCE_CODE_OMS {

	private String GROUP_CD;
	private String DEFINITION_CD;
	private String DEFINITION_NM;
	private String MEMO;
	private int REG_USER_CD;
	private Timestamp REG_DATE;
	private int MDF_USER_CD;
	private Timestamp MDF_DATE;

}
