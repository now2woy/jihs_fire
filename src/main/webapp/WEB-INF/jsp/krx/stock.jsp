<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
//페이지 설정 정보
var PAGE_CONFIG = [];
// DB 저장 URL
PAGE_CONFIG["SAVE_URL"] = "/api/krxs";
// 목록 URL
PAGE_CONFIG["LIST_URL"] = "/api/krxs";
// 수정 버튼 함수, "" 시 기본 함수
PAGE_CONFIG["MOD_BTN"] = "";
// 수정 버튼 여부
PAGE_CONFIG["MOD_BTN_YN"] = "E";
//페이지 사이즈
PAGE_CONFIG["PAGING_YN"] = "N";

//테이블 구성 정보
var data = [];

data.push({"NM" : "ITM_CD_",	"IDX" : "itmCd",	"VAL" : "itmCd",	"VAL2" : "",			"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "ITM_NM_",	"IDX" : "itmCd",	"VAL" : "itmNm",	"VAL2" : "",			"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : ""});
data.push({"NM" : "MKT_NM_",	"IDX" : "itmCd",	"VAL" : "mktNm",	"VAL2" : "mktCd",		"FUNC" : "",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "SPAC_YN_",	"IDX" : "itmCd",	"VAL" : "spacYn",	"VAL2" : "",			"FUNC" : "",	"TYPE" : "S",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "ITM_KND_NM_","IDX" : "itmCd",	"VAL" : "itmKndNm",	"VAL2" : "itmKndCd",	"FUNC" : "",	"TYPE" : "N",	"OPT" : "",		"TDST" : ""});
data.push({"NM" : "ITM_CL_NM_",	"IDX" : "itmCd",	"VAL" : "itmClNm",	"VAL2" : "itmClCd",		"FUNC" : "",	"TYPE" : "N",	"OPT" : "",		"TDST" : ""});

// 배치코드 셀렉트박스
var MKT_CD_SELECT;

$(document).ready(function () {
	
	//ls_table_init(LIST_URL);
	
	// 배치코드 셀렉트박스 생성
	PAGE_CONFIG["MKT_CD_SELECT"] = ct_cd_select("MKT_CD");
	
	// 검색 조건에 셀렉트박스 추가
	$("#DIV_SCH_MKT_CD").append(PAGE_CONFIG["MKT_CD_SELECT"].replace("#ID#", "SCH_MKT_CD"));
});

/**
* 등록 버튼
*/
function add(){
	// 기본 로직 처리 후 추가 처리
	ls_table_add();
	
	$("#TD_BATCH_NM_NEW").append(BATCH_CD_SELECT.replace("#ID#", "BATCH_NM_NEW"));
}

function sch(){
	$("#data tbody").empty();
	
	var schMktCd = $("#SCH_MKT_CD").val();
	var schItmNm = $("#SCH_ITM_NM").val();
	var schSpacYn = $('input:radio[name=SCH_SPAC_YN]:checked').val();
	
	ls_table_init(PAGE_CONFIG["LIST_URL"] + "?schMktCd=" + schMktCd + "&schItmNm=" + schItmNm + "&schSpacYn=" + schSpacYn, 1);
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
							<h2>검색</h2>
							<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
								<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
								<li><a class="close-link"><i class="fa fa-close"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<div class="form-group row col-md-6 col-sm-6 ">
								<div class="col-md-12 col-sm-12 " style="padding-bottom: 10px;">
									<label class="control-label col-md-3 col-sm-3 " style="padding-top: 10px;">시장</label>
									<div id="DIV_SCH_MKT_CD" class="col-md-8 col-sm-8 "></div>
								</div>
								<div class="col-md-12 col-sm-12 " style="padding-bottom: 10px;">
									<label class="control-label col-md-3 col-sm-3 " style="padding-top: 10px;">종목명</label>
									<div class="col-md-8 col-sm-8 ">
										<input type="text" id="SCH_ITM_NM" class="form-control" />
									</div>
								</div>
							</div>
							
							<div class="form-group row">
								<div class="col-md-12 col-sm-12">
									<label class="control-label col-md-3 col-sm-3 " style="padding-top: 10px;">스팩여부</label>
									<div class="col-md-8 col-sm-8 ">
										<div class="radio">
											<label>
												<input type="radio" name="SCH_SPAC_YN" class="flat" value="" checked="checked"> 선택
											</label>
										</div>
										<div class="radio">
											<label>
												<input type="radio" name="SCH_SPAC_YN" class="flat" value="Y"> 예
											</label>
										</div>
										<div class="radio">
											<label>
												<input type="radio" name="SCH_SPAC_YN" class="flat" value="N"> 아니오
											</label>
										</div>
									</div>
								</div>
							</div>
							
							<div class="ln_solid"></div>
							<div style="float: right;">
								<button type="button" class="btn btn-primary" onclick="sch();">조회</button>
							</div>
						</div>
					</div>
					
					<div class="x_panel">
						<div class="x_title">
							<h2>종목 목록</h2>
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
										<col style="width: 10%;" />
										<col />
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="width: 15%;" />
										<col style="width: 15%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">종목코드</th>
											<th class="column-title" style="text-align: center;">종목</th>
											<th class="column-title" style="text-align: center;">시장</th>
											<th class="column-title" style="text-align: center;">스팩여부</th>
											<th class="column-title" style="text-align: center;">주식종류</th>
											<th class="column-title" style="text-align: center;">주식구분</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					
					</div>
				</div>
			</div>
		<!-- /page content -->
		</div>
	</div>
</body>
</html>