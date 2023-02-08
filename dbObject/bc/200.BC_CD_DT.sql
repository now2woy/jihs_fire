/* 코드 상세 테이블 생성 */
CREATE TABLE BC_CD_DT(
	  CD_COL	VARCHAR(100)	NOT NULL	COMMENT '코드컬럼'
	, CD		VARCHAR(5)		NOT NULL	COMMENT '코드'
	, CD_NM		VARCHAR(100)	NOT NULL	COMMENT '코드명'
	, CD_VAL	VARCHAR(1000)	NULL		COMMENT '코드값'
	, ORD		DECIMAL(6)		NOT NULL	COMMENT '순서'
	, USE_YN	VARCHAR(1)		NOT NULL	COMMENT '사용여부'
) ENGINE=INNODB COMMENT='코드 정보';

/* PK 생성 */
ALTER TABLE BC_CD_DT ADD PRIMARY KEY (CD_COL, CD);

/* 기본 데이터 생성 쿼리 */
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('ACT_CD','00001','CMA',NULL,1,'Y'),
	 ('ACT_CD','00002','IRP',NULL,2,'Y'),
	 ('ACT_CD','00003','ISA',NULL,3,'Y');
	
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('BATCH_CD','00001','전자공시시스템 재무제표 수집','DART',1,'Y'),
	 ('BATCH_CD','00002','한국거래소 종목 거래 정보 수집','KRX',2,'Y'),
	 ('BATCH_CD','00003','한국거래소 종목 거래 정보 가공','KRX',3,'Y');
	
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('BK_CD','00001','NH투자증권',NULL,1,'Y'),
	 ('BK_CD','00002','신한투자증권',NULL,2,'Y'),
	 ('BK_CD','00003','KB국민은행',NULL,3,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('ITM_CL_CD','00001','주권',NULL,1,'Y'),
	 ('ITM_CL_CD','00002','부동산투자회사',NULL,2,'Y'),
	 ('ITM_CL_CD','00003','사회간접자본투융자회사',NULL,3,'Y'),
	 ('ITM_CL_CD','00004','투자회사',NULL,4,'Y'),
	 ('ITM_CL_CD','00005','선박투자회사',NULL,5,'Y'),
	 ('ITM_CL_CD','00006','외국주권',NULL,6,'Y'),
	 ('ITM_CL_CD','00007','주식예탁증권',NULL,7,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('ITM_KND_CD','00001','보통주',NULL,1,'Y'),
	 ('ITM_KND_CD','00002','구형우선주',NULL,2,'Y'),
	 ('ITM_KND_CD','00003','신형우선주',NULL,3,'Y'),
	 ('ITM_KND_CD','00004','종류주권',NULL,4,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('MKT_CD','KSQ','KOSDAQ',NULL,2,'Y'),
	 ('MKT_CD','STK','KOSPI',NULL,1,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('REPRT_CD','11011','4분기(사업)',NULL,4,'Y'),
	 ('REPRT_CD','11012','2분기(반기)',NULL,2,'Y'),
	 ('REPRT_CD','11013','1분기',NULL,1,'Y'),
	 ('REPRT_CD','11014','3분기',NULL,3,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('TAX_CL_CD','00001','배당소득세(보유기간과세)',NULL,1,'Y'),
	 ('TAX_CL_CD','00002','비과세',NULL,2,'Y'),
	 ('TAX_CL_CD','00003','배당소득세(해외주식투자전용ETF)',NULL,3,'Y'),
	 ('TAX_CL_CD','00004','배당소득세(분리과세부동산ETF)',NULL,4,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('TLGRM_CMD_CD','00001','전체 메뉴','?',1,'Y'),
	 ('TLGRM_CMD_CD','00002','계좌 목록','TLGRM_MSG_CD, 00001',2,'Y'),
	 ('TLGRM_CMD_CD','00003','계좌 선택','',3,'Y'),
	 ('TLGRM_CMD_CD','00004','NH투자증권 분배금 입력','NH투자증권, 분배금',4,'Y'),
	 ('TLGRM_CMD_CD','00005','NH투자증권 주문체결 입력','NH투자증권, 주문체결 알림',5,'Y'),
	 ('TLGRM_CMD_CD','00006','주문체결 계좌 선택','TLGRM_CMD_CD, 00005',6,'Y'),
	 ('TLGRM_MSG_CD','00001','계좌 목록','TLGRM_CMD_CD_00002',1,'Y');
	 
INSERT INTO BC_CD_DT (CD_COL,CD,CD_NM,CD_VAL,ORD,USE_YN) VALUES
	 ('TRD_CD','00001','입금',NULL,1,'Y'),
	 ('TRD_CD','00002','출금',NULL,2,'Y'),
	 ('TRD_CD','00003','이자',NULL,3,'Y'),
	 ('TRD_CD','00004','배당',NULL,4,'Y'),
	 ('TRD_CD','00005','분배',NULL,5,'Y'),
	 ('TRD_CD','00006','매수',NULL,6,'Y'),
	 ('TRD_CD','00007','매도',NULL,7,'Y'),
	 ('TRD_CD','00008','환전','',8,'Y');

/* 커밋 */
commit;