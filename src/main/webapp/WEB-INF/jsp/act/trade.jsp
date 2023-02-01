<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
//페이지 설정 정보
var PAGE_CONFIG = [];
// DB 저장 URL
PAGE_CONFIG["SAVE_URL"] = "/api/act/trades";
// 목록 URL
PAGE_CONFIG["LIST_URL"] = "/api/act/trades";
// 수정 버튼 함수, "" 시 기본 함수
PAGE_CONFIG["MOD_BTN"] = "mod";
// 수정 버튼 여부
PAGE_CONFIG["MOD_BTN_YN"] = "Y";
// 페이지 사이즈
PAGE_CONFIG["PAGING_YN"] = "Y";
//페이지 사이즈
PAGE_CONFIG["PAGE_SIZE"] = 10;
// 인덱스
PAGE_CONFIG["IDX"] = "trdSeq";
// 저장 함수
PAGE_CONFIG["INS_FUNC"] = "save";

//테이블 구성 정보
var data = [];

data.push({"NM" : "TRD_DT_",	"VAL" : "trdDt",	"VAL2" : "",		"FUNC" : "",	"TYPE" : "DT",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "TRD_CD_",	"VAL" : "trdNm",	"VAL2" : "trdCd",	"FUNC" : "",	"TYPE" : "N",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "AMT_",		"VAL" : "amt",		"VAL2" : "",		"FUNC" : "",	"TYPE" : "I",	"OPT" : "",		"TDST" : "T_R"});
data.push({"NM" : "ITM_CD_",	"VAL" : "itmNm",	"VAL2" : "itmCd",	"FUNC" : "",	"TYPE" : "TB",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "QTY_",		"VAL" : "qty",		"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "NOTE_",		"VAL" : "note",		"VAL2" : "",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "ED_DT_",		"VAL" : "edDt",		"VAL2" : "",		"FUNC" : "",	"TYPE" : "D",	"OPT" : "",		"TDST" : "T_C"});

$(document).ready(function () {
	var params = new URL(location.href).searchParams;
	PAGE_CONFIG["LIST_URL"] = "/api/act/trades/" + params.get("actSeq");
	
	ls_table_init(PAGE_CONFIG["LIST_URL"], 1, false);
	
	PAGE_CONFIG["TRD_CD_SELECT"] = ct_cd_select("/api/codes/TRD_CD", "cd", "cdNm");
	
	$("#IN_SUM_AMT").text(bc_num_format(PAGE_CONFIG["LIST_DATA"].inSumAmt) + "원");
	$("#OUT_SUM_AMT").text(bc_num_format(PAGE_CONFIG["LIST_DATA"].outSumAmt) + "원");
	$("#IN_OUT_SUM_AMT").text(bc_num_format(PAGE_CONFIG["LIST_DATA"].inOutSumAmt) + "원");
	$("#ITRST_SUM_AMT").text(bc_num_format(PAGE_CONFIG["LIST_DATA"].itrstSumAmt) + "원");
	$("#DVDN_SUM_AMT").text(bc_num_format(PAGE_CONFIG["LIST_DATA"].dvdnSumAmt) + "원");
});

/**
 * 등록 버튼
 */
function add(){
	ls_table_add();
	
	$("#TD_TRD_CD_NEW").append(PAGE_CONFIG["TRD_CD_SELECT"].replace("#ID#", "TRD_CD_NEW"));
}

/**
 * 목록 테이블 저장 버튼
 */
function save(id, method){
	if(confirm("저장하시겠습니까?")){
		var params = new URL(location.href).searchParams;
		var param = ct_save_data(id);
		
		param["actSeq"] = params.get("actSeq");
		
		$.ajax({
			url: PAGE_CONFIG["SAVE_URL"]
			, data : JSON.stringify(param)
			, method: method
			, dataType: "json"
			, contentType: 'application/json'
			, async : false
		})
		.done(function(json) {
			location.reload();
		});
	}
}

/**
 * 수정 버튼
 */
function mod(id){
	ls_table_mod(id);
	
	var trdNm = $("#TD_TRD_CD_" + id).text();
	
	$("#TD_TRD_CD_" + id).empty();
	$("#TD_TRD_CD_" + id).append(PAGE_CONFIG["TRD_CD_SELECT"].replace("#ID#", "TRD_CD_" + id));
	$("#TRD_CD_" + id + " option:contains('" + trdNm + "')").attr("selected", "selected");
}

/**
 * 목록 버튼
 */
function list() {
	location.href="/act/account.do";
}

/**
 * 액셀 업로드 팝업창 열기
 */
function pu_open(){
	var params = new URL(location.href).searchParams;
	
	$("#popup_title").text("Excel 업로드");
	$("#popup_body").append("<div><form id=\"excelUpload\" name=\"excelUpload\" method=\"POST\" action=\"/api/act/trades/excelUpload\" enctype=\"multipart/form-data\"><input type=\"hidden\" name=\"actSeq\" value=\"" + params.get("actSeq") + "\" /><input type=\"file\" name=\"file\" /></form></div>");
	$("#popup_body").append("<div style=\"float: right;\"><button type=\"button\" id=\"upload-btn\" class=\"btn btn-success\" onclick=\"excel_upload();\">업로드</button></div>");
	
	$("#popup_layer").css('display', 'block');
}

/**
 * 업로드 버튼
 */
function excel_upload(){
	var form = $('#excelUpload')[0];
	var formData = new FormData(form);
	
	$.ajax({
		  url: "/api/act/trades/excelUpload"
		, data: formData
		, cache: false
		, contentType: false
		, processData: false
		, method: 'POST'
		, type: 'POST'
		, success: function(data){
			alert("Excel 업로드에 성공하였습니다.");
			location.reload();
		}
	});
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
					<div class="x_panel">
						<div class="x_title">
							<h2>계좌 거래 요약</h2>
							<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
								<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
								<li><a class="close-link"><i class="fa fa-close"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<div class="table-responsive">
								<table class="table jambo_table bulk_action">
									<colgroup>
										<col style="width: 14%;" />
										<col style="width: 14%;" />
										<col style="width: 14%;" />
										<col style="width: 14%;" />
										<col style="width: 14%;" />
										<col style="width: 15%;" />
										<col style="width: 15%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">총 입금액 ①</th>
											<th class="column-title" style="text-align: center;">총 출금액 ②</th>
											<th class="column-title" style="text-align: center;">입출금 합계 (① + ②)</th>
											<th class="column-title" style="text-align: center;">총 이자금액 ③</th>
											<th class="column-title" style="text-align: center;">총 배당금액 ④</th>
											<th class="column-title" style="text-align: center;">총 수수료 ⑤</th>
											<th class="column-title" style="text-align: center;">평가금액 (① + ② + ③ + ④ + ⑤ + a)</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td id="IN_SUM_AMT" style="text-align: center;"></td>
											<td id="OUT_SUM_AMT" style="text-align: center;"></td>
											<td id="IN_OUT_SUM_AMT" style="text-align: center;"></td>
											<td id="ITRST_SUM_AMT" style="text-align: center;"></td>
											<td id="DVDN_SUM_AMT" style="text-align: center;"></td>
											<td id="" style="text-align: center;">수수료합계</td>
											<td id="" style="text-align: center;">평가금액</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<div class="x_panel">
						<div class="x_title">
							<h2>계좌 거래 목록</h2>
							<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
								<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
								<li><a class="close-link"><i class="fa fa-close"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<div class="table-responsive">
								<table id="data" class="table jambo_table bulk_action">
									<colgroup>
										<col style="width: 20%;" />
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="width: 15%;" />
										<col style="width: 10%;" />
										<col style="width: 20%;" />
										<col style="width: 10%;" />
										<col style="width: 5%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">거래일자</th>
											<th class="column-title" style="text-align: center;">거래유형</th>
											<th class="column-title" style="text-align: center;">금액</th>
											<th class="column-title" style="text-align: center;">종목</th>
											<th class="column-title" style="text-align: center;">수량</th>
											<th class="column-title" style="text-align: center;">비고</th>
											<th class="column-title" style="text-align: center;">만기일</th>
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
								<button type="button" id="list-btn" class="btn btn-secondary" onclick="list();">목록</button>
								<button type="button" id="excel-btn" class="btn btn-secondary" onclick="pu_open();">Excel 업로드</button>
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