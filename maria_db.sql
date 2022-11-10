/* 전자공시시스템API키 테이블 생성 */
CREATE TABLE DT_KEY_MT(
KEY varchar(100) not null COMMENT '키'
, ORD number(6) not null COMMENT '순서'
)ENGINE=INNODB COMMENT='전자공시시스템API키';

CREATE INDEX DT_KEY_MT_IDX_PK DT_KEY_MT(KEY);

ALTER TABLE DT_KEY_MT ADD PRIMARY KEY (KEY);

/* 코드 마스터 테이블 생성 */
CREATE TABLE BC_CD_MT(
CD_COL varchar(100) not null COMMENT '코드컬럼'
, CD_COL_NM varchar(200) not null COMMENT '코드컬럼명'
, USE_YN varchar(1) not null COMMENT '사용여부'
)ENGINE=INNODB COMMENT='코드마스터';

CREATE INDEX BC_CD_MT_IDX_PK BC_CD_MT(CD_COL);

ALTER TABLE BC_CD_MT ADD PRIMARY KEY (CD_COL);

/* 코드 상세 테이블 생성 */
CREATE TABLE BC_CD_DT(
 CD_COL varchar(100) not null COMMENT '코드컬럼'
, CD varchar(5) not null COMMENT '코드'
, CD_NM varchar(100) not null COMMENT '코드명'
, ORD number(6) not null COMMENT '순서'
, USE_YN varchar(1) not null COMMENT '사용여부'
PRIMARY KEY(CD_COL, CD) )ENGINE=INNODB COMMENT='코드상세';

CREATE INDEX BC_CD_DT_IDX_PK BC_CD_DT(CD_COL, CD);

ALTER TABLE BC_CD_MT ADD PRIMARY KEY (CD_COL, CD);



