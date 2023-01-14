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
	  USR_ID		VARCHAR(100)	not null		comment '사용자ID'
	, USR_NM		VARCHAR(100)	not null		comment '사용자명'
	, TLGRM_ID		VARCHAR(100)	NULL			comment '텔레그램ID'
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




