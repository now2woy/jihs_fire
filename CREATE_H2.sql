--------------------------------------------------------------------
/* DART */
--------------------------------------------------------------------
/* 전자공시시스템API키 테이블 생성 */
CREATE TABLE IF NOT EXISTS DT_KEY_MT (
	  API_KEY	VARCHAR(100)	NOT NULL	/* 키 */
	, ORD		NUMBER(6)		NOT NULL	/* 순서 */
);

ALTER TABLE DT_KEY_MT ADD PRIMARY KEY (API_KEY);

/* 전자공시시스템 재무제표 원본 */
CREATE TABLE IF NOT EXISTS DT_FNLTT_MT (
	  ITM_CD			VARCHAR(10)		NOT NULL	/* 종목코드 */
	, YR				VARCHAR(4)		NOT NULL	/* 연도(년도) */
	, QT				VARCHAR(1)		NOT NULL	/* 분기 */
	, SEQ				NUMBER(10)		NOT NULL	/* 일련번호 */
	, MKT_CD			VARCHAR(5)		NOT NULL	/* 시장코드 */
	, QT_CD				VARCHAR(5)		NULL		/* 분기코드 */
	, SJ_CD				VARCHAR(3)		NULL		/* 구분코드 */
	, SJ_NM				VARCHAR(10)		NULL		/* 구분명 */
	, ACNT_ID			VARCHAR(500)	NULL		/* 계정ID */
	, ACNT_NM			VARCHAR(500)	NULL		/* 계정명 */
	, ACNT_DTL			VARCHAR(500)	NULL		/* 계정상세 */
	, TH_TM_NM			VARCHAR(50)		NULL		/* 이번분기명 */
	, TH_TM_AMT			VARCHAR(30)		NULL		/* 이번분기값 */
	, TH_TM_ADD_AMT		VARCHAR(30)		NULL		/* 이번분기누적값 */
	, FRM_TM_NM			VARCHAR(50)		NULL		/* 이전분기명 */
	, FRM_TM_AMT		VARCHAR(30)		NULL		/* 이전분기값 */
	, FRM_TM_ADD_AMT	VARCHAR(30)		NULL		/* 이전분기누적값 */
	, ORD				NUMBER(6)		NULL		/* 순번 */
);

ALTER TABLE DT_FNLTT_MT ADD PRIMARY KEY (ITM_CD, YR, QT, SEQ);
--------------------------------------------------------------------
/* KRX */
--------------------------------------------------------------------
CREATE TABLE KX_ITM_MT (
	  ITM_CD			VARCHAR(10)		NOT NULL	/* 종목코드 */
	, ITM_NM			VARCHAR(100)	NOT NULL	/* 종목명 */
	, MKT_CD			VARCHAR(5)		NOT NULL	/* 시장코드 */
	, STD_ITM_CD		VARCHAR(20)		NULL		/* 표준종목코드 */
	, PUB_DT			DATE			NULL		/* 상장일자 */
	, SPAC_YN			VARCHAR(1)		NULL		/* 스팩여부 */
);

ALTER TABLE KX_ITM_MT ADD PRIMARY KEY (ITM_CD);

--------------------------------------------------------------------
/* BASIC */
--------------------------------------------------------------------
/* 코드 마스터 테이블 생성 */
CREATE TABLE BC_CD_MT (
	  CD_COL	VARCHAR(100)	NOT NULL	/* 코드컬럼 */
	, CD_COL_NM	VARCHAR(200)	NOT NULL	/* 코드컬럼명 */
	, USE_YN	VARCHAR(1)		NOT NULL	/* 사용여부 */
);

ALTER TABLE BC_CD_MT ADD PRIMARY KEY (CD_COL);

/* 코드 상세 테이블 생성 */
CREATE TABLE BC_CD_DT(
	  CD_COL	VARCHAR(100)	NOT NULL	/* 코드컬럼 */
	, CD		VARCHAR(5)		NOT NULL	/* 코드 */
	, CD_NM		VARCHAR(100)	NOT NULL	/* 코드명 */
	, ORD		NUMBER(6)		NOT NULL	/* 순서 */
	, USE_YN	VARCHAR(1)		NOT NULL	/* 사용여부 */
);

ALTER TABLE BC_CD_DT ADD PRIMARY KEY (CD_COL, CD);






