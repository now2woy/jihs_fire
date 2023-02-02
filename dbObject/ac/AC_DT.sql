/*
 * 계좌 거래 정보 테이블 생성
 */
CREATE TABLE AC_DT (
	  TRD_SEQ		DECIMAL(20)		NOT NULL	comment '거래일련번호'
	, ACT_SEQ		DECIMAL(6)		NOT NULL	comment '계좌일련번호'
	, REL_TRD_SEQ	DECIMAL(20)		NULL		comment '관련거래일련번호'
	, TRD_CD		VARCHAR(5)		NOT NULL	comment '거래코드'
	, AMT			DECIMAL(20)		NULL		comment '금액'
	, ITM_CD		VARCHAR(10)		NULL		COMMENT '종목코드'
	, QTY			DECIMAL(20)		NULL		comment '수량'
	, FEE			DECIMAL(20)		NULL		comment '수수료'
	, TAX			DECIMAL(20)		NULL		comment '세금'
	, NOTE			VARCHAR(4000)	NULL		comment '비고'
	, TLGRM_MSG_ID	VARCHAR(100)	NULL		comment '텔레그램메시지ID'
	, TRD_DT		DATETIME		NULL		comment '거래일시'
	, ED_DT			DATETIME		NULL		comment '만료일시'
) ENGINE=INNODB COMMENT '계좌 거래내역';


/*
 * 계좌 거래 정보 테이블 PK 생성
 */
ALTER TABLE AC_DT ADD PRIMARY KEY (TRD_SEQ);