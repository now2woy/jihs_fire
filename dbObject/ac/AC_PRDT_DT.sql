/*
 * 계좌 상품 거래 정보 테이블 생성
 */
CREATE TABLE AC_PRDT_DT (
	  PRDT_TRD_SEQ		DECIMAL(30)		NOT NULL	comment '상품거래일련번호'
	, ITM_CD			VARCHAR(10)		NULL		COMMENT '종목코드'
	, BY_TRD_SEQ		DECIMAL(20)		NOT NULL	comment '매수거래일련번호'
	, BY_AMT			DECIMAL(20)		NULL		comment '매수금액'
	, BY_DT				DATETIME		NULL		comment '매수일시'
	, BY_TLGRM_MSG_ID	VARCHAR(100)	NULL		comment '매수텔레그램메시지ID'
	, SL_TRD_SEQ		DECIMAL(20)		NOT NULL	comment '매도거래일련번호'
	, SL_AMT			DECIMAL(20)		NULL		comment '매도금액'
	, SL_DT				DATETIME		NULL		comment '매도일시'
	, SL_TLGRM_MSG_ID	VARCHAR(100)	NULL		comment '매도텔레그램메시지ID'
) ENGINE=INNODB COMMENT '계좌상품거래내역';


/*
 * 계좌 상품 거래 정보 테이블 PK 생성
 */
ALTER TABLE AC_PRDT_DT ADD PRIMARY KEY (PRDT_TRD_SEQ);