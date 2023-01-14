/**
 * 한국거래소 주식종목 거래 가공 정보 테이블 생성
 */
CREATE TABLE KX_TRD_DT (
	   ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	 , DT				DATE			NOT NULL	COMMENT '거래일자'
	 , TNOV_RT			DECIMAL(20, 2)	NULL		COMMENT '회전율'
	 , DY_005_AVG_AMT	DECIMAL(20)		NULL		COMMENT '5일 이동평균금액'
	 , DY_020_AVG_AMT	DECIMAL(20)		NULL		COMMENT '20일 이동평균금액'
	 , DY_060_AVG_AMT	DECIMAL(20)		NULL		COMMENT '60일 이동평균금액'
	 , DY_120_AVG_AMT	DECIMAL(20)		NULL		COMMENT '120일 이동평균금액'
	 , EPS				DECIMAL(20, 2)	NULL		COMMENT 'EPS(주당순이익)'
	 , BPS				DECIMAL(20, 2)	NULL		COMMENT 'BPS(주당순자산가치)'
	 , CPS				DECIMAL(20, 2)	NULL		COMMENT 'CPS(주당현금흐름)'
	 , SPS				DECIMAL(20, 2)	NULL		COMMENT 'SPS(주당매출액)'
	 , PER				DECIMAL(20, 2)	NULL		COMMENT 'PER(주가수익비율)'
	 , PBR				DECIMAL(20, 2)	NULL		COMMENT 'PBR(주가순자산비율)'
	 , PCR				DECIMAL(20, 2)	NULL		COMMENT 'PCR(주가현금흐름비율)'
	 , PSR				DECIMAL(20, 2)	NULL		COMMENT 'PSR(주가매출비율)'
	 
) ENGINE=INNODB COMMENT='한국거래소 주식종목 거래 가공 정보';

/**
 * 한국거래소 주식종목 거래 가공 정보 테이블 생성
 */
ALTER TABLE KX_TRD_DT ADD PRIMARY KEY (ITM_CD, DT);

/**
 * 한국거래소 주식종목 거래 가공 정보 인덱스 생성
 */
CREATE INDEX KX_TRD_DT_IDX_001 ON KX_TRD_DT(DT, ITM_CD);