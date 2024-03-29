/**
 * 전자공시시스템 재무제표 원본
 */
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

/**
 * 전자공시시스템 재무제표 원본
 */
ALTER TABLE DT_FNLTT_MT ADD PRIMARY KEY (DART_ITM_CD, YR, QT_CD, SEQ);