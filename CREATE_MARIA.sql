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
	  DART_ITM_CD		VARCHAR(10)		NOT NULL	COMMENT '전자공시시스템 종목코드'
	, YR				VARCHAR(4)		NOT NULL	COMMENT '연도(년도)'
	, QT_CD				VARCHAR(5)		NOT NULL	COMMENT '분기코드'
	, SEQ				DECIMAL(10)		NOT NULL	COMMENT '일련번호'
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
	, CRC_CD			VARCHAR(5)		NULL		COMMENT '통화코드'
	, ITM_CD			VARCHAR(10)		NULL		COMMENT '종목코드'
	, FS_CD				VARCHAR(5)		NULL		COMMENT '금융코드'
) ENGINE=INNODB COMMENT='전자공시시스템 재무제표 원본';

ALTER TABLE DT_FNLTT_MT ADD PRIMARY KEY (DART_ITM_CD, YR, QT_CD, SEQ);
--------------------------------------------------------------------
/* KRX */
--------------------------------------------------------------------
CREATE TABLE KX_ITM_MT (
	  ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	, ITM_NM			VARCHAR(200)	NOT NULL	COMMENT '종목명'
	, MKT_CD			VARCHAR(5)		NOT NULL	COMMENT '시장코드'
	, STD_ITM_CD		VARCHAR(20)		NULL		COMMENT '표준종목코드'
	, PUB_DT			DATE			NULL		COMMENT '상장일자'
	, SPAC_YN			VARCHAR(1)		NULL		COMMENT '스팩여부'
	, ITM_KND_CD		VARCHAR(5)		NULL		COMMENT '주식종류코드'
	, ITM_CL_CD			VARCHAR(5)		NULL		COMMENT '주식구분코드'
	, TAX_CL_CD			VARCHAR(5)		NULL		COMMENT '과세유형코드'
) ENGINE=INNODB COMMENT='한국거래소 주식종목 마스터';

ALTER TABLE KX_ITM_MT ADD PRIMARY KEY (ITM_CD);

CREATE TABLE KX_ITM_DT (
	   ITM_CD			VARCHAR(10)		NOT NULL	COMMENT '종목코드'
	 , DT				DATE			NOT NULL	COMMENT '변경일자'
	 , ISU_STK_QTY		DECIMAL(20)		NULL		COMMENT '상장주식수'
) ENGINE=INNODB COMMENT='한국거래소 주식종목 추가정보';

ALTER TABLE KX_ITM_DT ADD PRIMARY KEY (ITM_CD, DT);

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

ALTER TABLE KX_TRD_MT ADD PRIMARY KEY (ITM_CD, DT);

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

ALTER TABLE KX_TRD_DT ADD PRIMARY KEY (ITM_CD, DT);




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
	  CD_COL	VARCHAR(100)	NOT NULL	COMMENT '코드컬럼'
	, CD		VARCHAR(5)		NOT NULL	COMMENT '코드'
	, CD_NM		VARCHAR(100)	NOT NULL	COMMENT '코드명'
	, ORD		DECIMAL(6)		NOT NULL	COMMENT '순서'
	, USE_YN	VARCHAR(1)		NOT NULL	COMMENT '사용여부'
) ENGINE=INNODB COMMENT='코드상세';

ALTER TABLE BC_CD_DT ADD PRIMARY KEY (CD_COL, CD);

/* 배치 정보 테이블 생성 */
CREATE TABLE BC_BATCH_MT (
	  SEQ		DECIMAL(10)		NOT NULL				COMMENT '일련번호'
	, BATCH_CD	VARCHAR(5)		NOT NULL				COMMENT '코드컬럼'
	, PARM_1ST	VARCHAR(100)	NULL					COMMENT '파라미터 1'
	, PARM_2ND	VARCHAR(100)	NULL					COMMENT '파라미터 2'
	, PARM_3RD	VARCHAR(100)	NULL					COMMENT '파라미터 3'
	, PARM_4TH	VARCHAR(100)	NULL					COMMENT '파라미터 4'
	, EXE_YN	VARCHAR(1)		NOT NULL	DEFAULT 'N'	COMMENT '실행여부'
	, SUC_YN	VARCHAR(1)		NULL					COMMENT '성공여부'
	, EXE_ST_DT	DATETIME		NULL					COMMENT '실행시작일시'
	, EXE_ED_DT	DATETIME		NULL					COMMENT '실행종료일시'
) ENGINE=INNODB COMMENT='배치 정보';

ALTER TABLE BC_BATCH_MT ADD PRIMARY KEY (SEQ);

/* 사용자 정보 테이블 생성 */
CREATE TABLE BC_US_MT (
	  USR_ID	VARCHAR(100)	not null		comment '사용자ID'
	, USR_NM	VARCHAR(100)	not null		comment '사용자명'
) ENGINE=INNODB COMMENT='사용자 정보';

ALTER TABLE BC_US_MT ADD PRIMARY KEY (USR_ID);

/* 채번 정보 테이블 생성 */
CREATE TABLE BC_NO_GEN (
	  GEN_KEY	VARCHAR(100)	NOT NULL	comment '채번키'
	, NUM		DECIMAL(20)		NOT NULL	comment '번호'
) ENGINE=INNODB COMMENT '채번';

ALTER TABLE BC_NO_GEN ADD PRIMARY KEY (GEN_KEY);


--------------------------------------------------------------------
/* ACCOUNT */
--------------------------------------------------------------------

/* 계좌 정보 테이블 생성 */
CREATE TABLE AC_MT (
	  ACT_SEQ	DECIMAL(6)		not null				comment '계좌일련번호'
	, ACT_NM	VARCHAR(200)	not null				comment '계좌명'
	, ACT_CD	VARCHAR(5)		not null				comment '계좌코드'
	, BK_CD		VARCHAR(5)		not null				comment '은행코드'
	, USR_ID	VARCHAR(100)	not null				comment '사용자ID'
	, USE_YN	VARCHAR(1)		not null	default 'Y'	comment '사용여부'
) ENGINE=INNODB COMMENT '계좌 정보';

ALTER TABLE AC_MT ADD PRIMARY KEY (ACT_SEQ);

/* 계좌 거래 정보 테이블 생성 */
CREATE TABLE AC_DT (
	  TRD_SEQ		DECIMAL(20)		NOT NULL	comment '거래일련번호'
	, ACT_SEQ		DECIMAL(6)		NOT NULL	comment '계좌일련번호'
	, REL_TRD_SEQ	DECIMAL(20)		NULL		comment '관련거래일련번호'
	, TRD_CD		VARCHAR(5)		NOT NULL	comment '거래코드'
	, AMT			DECIMAL(20)		NULL		comment '금액'
	, ITM_CD		VARCHAR(10)		NULL		COMMENT '종목코드'
	, QTY			DECIMAL(20)		NULL		comment '수량'
	, NOTE			VARCHAR(4000)	NULL		comment '비고'
	, TRD_DT		DATETIME		NULL		comment '거래일시'
	, ED_DT			DATETIME		NULL		comment '만료일시'
) ENGINE=INNODB COMMENT '계좌 거래내역';

ALTER TABLE AC_DT ADD PRIMARY KEY (TRD_SEQ);




