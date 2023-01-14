/**
 * 전자공시시스템 주식종목 마스터 테이블 생성
 */
CREATE TABLE DT_ITM_MT (
	  DART_ITM_CD		VARCHAR(10)		NOT NULL	COMMENT '전자공시시스템 종목코드'
	, DART_ITM_CD_NM	VARCHAR(100)	NOT NULL	COMMENT '전자공시시스템 종목코드명'
	, ITM_CD			VARCHAR(10)		NULL		COMMENT '종목코드'
	, MOD_DT			DATE			NULL		COMMENT '상장일자'
) ENGINE=INNODB COMMENT='전자공시시스템 주식종목 마스터';

/**
 * 전자공시시스템 주식종목 마스터 테이블 PK 생성
 */
ALTER TABLE DT_ITM_MT ADD PRIMARY KEY (DART_ITM_CD);