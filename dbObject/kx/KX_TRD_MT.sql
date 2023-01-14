/**
 * 한국거래소 주식종목 거래 정보 테이블 생성
 */
CREATE TABLE KX_TRD_MT (
	   ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	 , DT				DATE			NOT NULL	COMMENT '거래일자'
	 , ST_AMT			DECIMAL(20)		NULL		COMMENT '시가'
	 , ED_AMT			DECIMAL(20)		NULL		COMMENT '종가'
	 , LW_AMT			DECIMAL(20)		NULL		COMMENT '저가'
	 , HG_AMT			DECIMAL(20)		NULL		COMMENT '고가'
	 , INCR_AMT			DECIMAL(20)		NULL		COMMENT '증감액'
	 , TRD_QTY			DECIMAL(20)		NULL		COMMENT '거래량'
	 , MKT_TOT_AMT		DECIMAL(30)		NULL		COMMENT '시가총액'
	 , ISU_STK_QTY		DECIMAL(30)		NULL		COMMENT '발행주식수'
) ENGINE=INNODB COMMENT='한국거래소 주식종목 거래 정보';

/**
 * 한국거래소 주식종목 거래 정보 테이블 생성
 */
ALTER TABLE KX_TRD_MT ADD PRIMARY KEY (ITM_CD, DT);

/**
 * 한국거래소 주식종목 거래 정보 인덱스 생성
 */
CREATE INDEX KX_TRD_MT_IDX_001 ON KX_TRD_MT(DT, ITM_CD);
