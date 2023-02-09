/* 파일 정보 테이블 생성 */
CREATE TABLE BC_FILE_MT	(
	  FILE_SEQ		DECIMAL(20)		NOT NULL	COMMENT '파일일련번호'
	, FILE_NM		VARCHAR(255)	NOT NULL	COMMENT '파일명'
	, ORG_FILE_NM	VARCHAR(255)	NOT NULL	COMMENT '원본파일명'
) ENGINE=INNODB COMMENT='파일 정보';

ALTER TABLE BC_FILE_MT ADD PRIMARY KEY (FILE_SEQ);