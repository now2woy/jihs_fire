<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
// 페이지 설정 정보
var PAGE_CONFIG = [];
// DB 저장 URL
PAGE_CONFIG["SAVE_URL"] = "/api/batchs";
// 목록 URL
PAGE_CONFIG["LIST_URL"] = "/api/batchs";
// 수정 버튼 함수, "" 시 기본 함수
PAGE_CONFIG["MOD_BTN"] = "";
// 수정 버튼 여부
PAGE_CONFIG["MOD_BTN_YN"] = "N";
// 페이지 사이즈
PAGE_CONFIG["PAGING_YN"] = "Y";
// 페이지 사이즈
PAGE_CONFIG["PAGE_SIZE"] = 10;
//인덱스
PAGE_CONFIG["IDX"] = "seq";

//테이블 구성 정보
var data = [];

data.push({"NM" : "SEQ_",		"VAL" : "seq",		"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "O_R",	"TDST" : "T_C"});
data.push({"NM" : "BATCH_NM_",	"VAL" : "batchNm",	"VAL2" : "batchCd",	"FUNC" : "",	"TYPE" : "N",	"OPT" : "",		"TDST" : ""});
data.push({"NM" : "PARM_1ST_",	"VAL" : "parm1st",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "PARM_2ND_",	"VAL" : "parm2nd",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "PARM_3RD_",	"VAL" : "parm3rd",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "PARM_4TH_",	"VAL" : "parm4th",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "EXE_YN_",	"VAL" : "exeYn",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "S",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "SUC_YN_",	"VAL" : "sucYn",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "S",	"OPT" : "O_D",	"TDST" : "T_C"});
data.push({"NM" : "EXE_ST_DT_",	"VAL" : "exeStDt",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "O_R",	"TDST" : "T_C"});
data.push({"NM" : "EXE_ED_DT_",	"VAL" : "exeEdDt",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "O_R",	"TDST" : "T_C"});

$(document).ready(function () {
	// 목록 초기화
	ls_table_init(PAGE_CONFIG["LIST_URL"], 1);
	
	// 배치코드 셀렉트박스 생성
	PAGE_CONFIG["BATCH_CD_SELECT"] = ct_cd_select("/api/codes/BATCH_CD", "cd", "cdNm");
	
	// 검색 조건에 셀렉트박스 추가
	$("#DIV_SCH_BATCH_CD").append(PAGE_CONFIG["BATCH_CD_SELECT"].replace("#ID#", "SCH_BATCH_CD"));
});

/**
* 등록 버튼
*/
function add(){
	// 기본 로직 처리 후 추가 처리
	ls_table_add();
	
	$("#TD_BATCH_NM_NEW").append(PAGE_CONFIG["BATCH_CD_SELECT"].replace("#ID#", "BATCH_NM_NEW"));
}

/**
 * 검색
 */
function sch(){
	$("#data tbody").empty();
	
	collapseLinkOnClick($("#search-fa-chevron"));
	
	var schBatchCd = $("#SCH_BATCH_CD").val();
	var schExeYn = $('input:radio[name=SCH_EXE_YN]:checked').val();
	
	ls_table_init(PAGE_CONFIG["LIST_URL"] + "?schBatchCd=" + schBatchCd + "&schExeYn=" + schExeYn, 1);
}
</script>
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
		
		<!-- 왼쪽 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/left.jsp" %>
		<!-- 왼쪽 메뉴 종료 -->
		
		<!-- 상단 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
		<!-- 상단 메뉴 종료 -->
			
		<!-- page content -->
			<div class="right_col" role="main" style="min-height: 929px;">
				<div class="">
					<div class="clearfix"></div>
					<div class="x_panel" style="height: auto;">
					<form action="/bsc/batch.do" method="get">
						<div class="x_title">
							<h2>검색</h2>
							<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
								<li><a id="search-fa-chevron" class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
								<li><a class="close-link"><i class="fa fa-close"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="x_content" style="display: none;">
							<div class="form-group row col-md-6 col-sm-6 ">
								<label class="control-label col-md-3 col-sm-3 " style="padding-top: 10px;">배치명</label>
								<div id="DIV_SCH_BATCH_CD" class="col-md-8 col-sm-8 "></div>
							</div>
							
							<div class="form-group row">
								<label class="control-label col-md-3 col-sm-3 " style="padding-top: 10px;">실행여부</label>
								<div class="col-md-8 col-sm-8 ">
									<div class="radio">
										<label>
											<input type="radio" name="SCH_EXE_YN" class="flat" value="" checked="checked"> 선택
										</label>
									</div>
									<div class="radio">
										<label>
											<input type="radio" name="SCH_EXE_YN" class="flat" value="Y"> 예
										</label>
									</div>
									<div class="radio">
										<label>
											<input type="radio" name="SCH_EXE_YN" class="flat" value="N"> 아니오
										</label>
									</div>
								</div>
							</div>
							
							<div class="ln_solid"></div>
							<div style="float: right;">
								<button type="button" class="btn btn-primary" onclick="sch();">조회</button>
							</div>
						</div>
					</form>
					</div>
					
					<div class="x_panel">
						<div class="x_title">
							<h2>배치 목록</h2>
							<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
								<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
								<li><a class="close-link"><i class="fa fa-close"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						
						<div class="row">
							<div class="col-sm-5">
								<div class="dataTables_info" id="datatable-keytable_info" role="status" aria-live="polite" style="margin-left: 5px;"> 전체 57건 중 1페이지</div>
							</div>
						</div>
						
						<div class="x_content">
							<div class="table-responsive">
								<table id="data" class="table jambo_table bulk_action">
									<colgroup>
										<col style="width: 5%;" />
										<col style="width: 20%;" />
										<col style="width: 9%;" />
										<col style="width: 9%;" />
										<col style="width: 9%;" />
										<col style="width: 9%;" />
										<col style="width: 7%;" />
										<col style="width: 7%;" />
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="width: 5%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">번호</th>
											<th class="column-title" style="text-align: center;">배치명</th>
											<th class="column-title" style="text-align: center;">변수1</th>
											<th class="column-title" style="text-align: center;">변수2</th>
											<th class="column-title" style="text-align: center;">변수3</th>
											<th class="column-title" style="text-align: center;">변수4</th>
											<th class="column-title" style="text-align: center;">실행여부</th>
											<th class="column-title" style="text-align: center;">성공여부</th>
											<th class="column-title" style="text-align: center;">실행시작일시</th>
											<th class="column-title" style="text-align: center;">실행종료일시</th>
											<th class="column-title" style="text-align: center;">-</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							
							<div class="row">
								<div class="col-sm-5"></div>
								<div class="col-sm-7">
									<div id="paginate" class="dataTables_paginate paging_simple_numbers"></div>
								</div>
							</div>
							
						<!-- 버튼 영역 시작 -->
							<div style="float: right;">
								<button type="button" id="add-btn" class="btn btn-success" onclick="add();">등록</button>
							</div>
						<!-- 버튼 영역 종료 -->
						</div>
					
					</div>
				</div>
			</div>
		<!-- /page content -->
		</div>
	</div>
</body>
</html>