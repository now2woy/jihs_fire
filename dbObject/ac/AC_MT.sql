/*
 * 계좌 정보 테이블 생성
 */
CREATE TABLE AC_MT (
	  ACT_SEQ	DECIMAL(6)		not null				comment '계좌일련번호'
	, ACT_NM	VARCHAR(200)	not null				comment '계좌명'
	, ACT_CD	VARCHAR(5)		not null				comment '계좌코드'
	, ACT_NO	VARCHAR(100)	null					comment '계좌번호'
	, BK_CD		VARCHAR(5)		not null				comment '은행코드'
	, USR_ID	VARCHAR(100)	not null				comment '사용자ID'
	, USE_YN	VARCHAR(1)		not null	default 'Y'	comment '사용여부'
) ENGINE=INNODB COMMENT '계좌 정보';

/*
 * 계좌 정보 테이블 PK 생성
 */
ALTER TABLE AC_MT ADD PRIMARY KEY (ACT_SEQ);