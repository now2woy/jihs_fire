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








