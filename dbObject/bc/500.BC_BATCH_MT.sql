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

INSERT INTO BC_BATCH_MT VALUES ('1',	'00001',	'2014',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('2',	'00001',	'2014',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('3',	'00001',	'2014',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('4',	'00001',	'2014',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('5',	'00001',	'2015',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('6',	'00001',	'2015',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('7',	'00001',	'2015',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('8',	'00001',	'2015',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('9',	'00001',	'2016',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('10',	'00001',	'2016',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('11',	'00001',	'2016',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('12',	'00001',	'2016',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('13',	'00001',	'2017',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('14',	'00001',	'2017',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('15',	'00001',	'2017',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('16',	'00001',	'2017',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('17',	'00001',	'2018',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('18',	'00001',	'2018',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('19',	'00001',	'2018',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('20',	'00001',	'2018',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('21',	'00001',	'2019',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('22',	'00001',	'2019',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('23',	'00001',	'2019',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('24',	'00001',	'2019',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('25',	'00001',	'2020',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('26',	'00001',	'2020',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('27',	'00001',	'2020',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('28',	'00001',	'2020',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('29',	'00001',	'2021',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('30',	'00001',	'2021',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('31',	'00001',	'2021',	'11014',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('32',	'00001',	'2021',	'11011',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('33',	'00001',	'2022',	'11013',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('34',	'00001',	'2022',	'11012',	'',	'',	'N',	'',	null,	null);
INSERT INTO BC_BATCH_MT VALUES ('35',	'00001',	'2022',	'11014',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('36',	'00002',	'20140101',	'',	'',	'',	'N',	'',	null,	null);

INSERT INTO BC_BATCH_MT VALUES ('37',	'00003',	'20150101',	'',	'',	'',	'N',	'',	null,	null);

UPDATE BC_NO_GEN SET NUM = '37' WHERE GEN_KEY = 'BC_BATCH_MT.SEQ';

/* 커밋 */
commit;