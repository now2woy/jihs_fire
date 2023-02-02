/**
 * 한국거래소 주식종목 동의어 테이블 생성
 */
CREATE TABLE KX_ITM_SYN_DT (
	  SYN_SEQ			DECIMAL(20)		NOT NULL	comment '동의어일련번호'
	, ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	, ITM_NM			VARCHAR(200)	NOT NULL	COMMENT '종목명'
)
	ENGINE=INNODB
	COMMENT='한국거래소 주식종목 동의어';

/**
 * 한국거래소 주식종목 동의어 테이블 PK 생성
 */
ALTER TABLE KX_ITM_SYN_DT ADD PRIMARY KEY (SYN_SEQ);