/* 사용자 정보 테이블 생성 */
CREATE TABLE BC_US_MT (
	  USR_ID		VARCHAR(100)	not null		comment '사용자ID'
	, USR_NM		VARCHAR(100)	not null		comment '사용자명'
	, TLGRM_ID		VARCHAR(100)	NULL			comment '텔레그램ID'
) ENGINE=INNODB COMMENT='사용자 정보';

ALTER TABLE BC_US_MT ADD PRIMARY KEY (USR_ID);