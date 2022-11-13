--------------------------------------------------------------------
/* DART */
--------------------------------------------------------------------
/* 전자공시시스템API키 테이블 생성 */
CREATE TABLE DT_KEY_MT (
	  API_KEY	VARCHAR(100)	NOT NULL	COMMENT '키'
	, ORD		DECIMAL(6)		NOT NULL	COMMENT '순서'
) ENGINE=INNODB COMMENT='전자공시시스템API키';

ALTER TABLE DT_KEY_MT ADD PRIMARY KEY (API_KEY);

/* 전자공시시스템 주식종목 마스터 테이블 생성 */
CREATE TABLE DT_ITM_MT (
	  DART_ITM_CD		VARCHAR(10)		NOT NULL	COMMENT '전자공시시스템 종목코드'
	, DART_ITM_CD_NM	VARCHAR(100)	NOT NULL	COMMENT '전자공시시스템 종목코드명'
	, ITM_CD			VARCHAR(10)		NULL		COMMENT '종목코드'
	, MOD_DT			DATE			NULL		COMMENT '상장일자'
) ENGINE=INNODB COMMENT='전자공시시스템 주식종목 마스터';

ALTER TABLE DT_ITM_MT ADD PRIMARY KEY (DART_ITM_CD);

/* 전자공시시스템 재무제표 원본 */
CREATE TABLE IF NOT EXISTS DT_FNLTT_MT (
	ITM_CD				VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	, YR				VARCHAR(4)		NOT NULL	COMMENT '연도(년도)'
	, QT				VARCHAR(1)		NOT NULL	COMMENT '분기'
	, SEQ				DECIMAL(10)		NOT NULL	COMMENT '일련번호'
	, MKT_CD			VARCHAR(5)		NOT NULL	COMMENT '시장코드'
	, QT_CD				VARCHAR(5)		NULL		COMMENT '분기코드'
	, SJ_CD				VARCHAR(3)		NULL		COMMENT '구분코드'
	, SJ_NM				VARCHAR(10)		NULL		COMMENT '구분명'
	, ACNT_ID			VARCHAR(500)	NULL		COMMENT '계정ID'
	, ACNT_NM			VARCHAR(500)	NULL		COMMENT '계정명'
	, ACNT_DTL			VARCHAR(500)	NULL		COMMENT '계정상세'
	, TH_TM_NM			VARCHAR(50)		NULL		COMMENT '이번분기명'
	, TH_TM_AMT			VARCHAR(30)		NULL		COMMENT '이번분기값'
	, TH_TM_ADD_AMT		VARCHAR(30)		NULL		COMMENT '이번분기누적값'
	, FRM_TM_NM			VARCHAR(50)		NULL		COMMENT '이전분기명'
	, FRM_TM_AMT		VARCHAR(30)		NULL		COMMENT '이전분기값'
	, FRM_TM_ADD_AMT	VARCHAR(30)		NULL		COMMENT '이전분기누적값'
	, ORD				DECIMAL(6)		NULL		COMMENT '순번'
) ENGINE=INNODB COMMENT='전자공시시스템 재무제표 원본';

ALTER TABLE DT_FNLTT_MT ADD PRIMARY KEY (ITM_CD, YR, QT, SEQ);
--------------------------------------------------------------------
/* KRX */
--------------------------------------------------------------------
CREATE TABLE KX_ITM_MT (
	  ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	, ITM_NM			VARCHAR(100)	NOT NULL	COMMENT '종목명'
	, MKT_CD			VARCHAR(5)		NOT NULL	COMMENT '시장코드'
	, STD_ITM_CD		VARCHAR(20)		NULL		COMMENT '표준종목코드'
	, PUB_DT			DATE			NULL		COMMENT '상장일자'
	, SPAC_YN			VARCHAR(1)		NULL		COMMENT '스팩여부'
	, ITM_KND_CD		VARCHAR(5)		NULL		COMMENT '주식종류코드'
	, ITM_CL_CD			VARCHAR(5)		NULL		COMMENT '주식구분코드'
) ENGINE=INNODB COMMENT='한국거래소 주식종목 마스터';

ALTER TABLE KX_ITM_MT ADD PRIMARY KEY (ITM_CD);

--------------------------------------------------------------------
/* BASIC */
--------------------------------------------------------------------
/* 코드 마스터 테이블 생성 */
CREATE TABLE BC_CD_MT (
	  CD_COL	VARCHAR(100)	NOT NULL	COMMENT '코드컬럼'
	, CD_COL_NM	VARCHAR(200)	NOT NULL	COMMENT '코드컬럼명'
	, USE_YN	VARCHAR(1)		NOT NULL	COMMENT '사용여부'
) ENGINE=INNODB COMMENT='코드마스터';

ALTER TABLE BC_CD_MT ADD PRIMARY KEY (CD_COL);

/* 코드 상세 테이블 생성 */
CREATE TABLE BC_CD_DT(
	  CD_COL	VARCHAR(100)	NOT NULL COMMENT '코드컬럼'
	, CD		VARCHAR(5)		NOT NULL COMMENT '코드'
	, CD_NM		VARCHAR(100)	NOT NULL COMMENT '코드명'
	, ORD		DECIMAL(6)		NOT NULL COMMENT '순서'
	, USE_YN	VARCHAR(1)		NOT NULL COMMENT '사용여부'
) ENGINE=INNODB COMMENT='코드상세';

ALTER TABLE BC_CD_DT ADD PRIMARY KEY (CD_COL, CD);




