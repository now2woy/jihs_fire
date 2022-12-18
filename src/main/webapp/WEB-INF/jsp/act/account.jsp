<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
//페이지 설정 정보
var PAGE_CONFIG = [];
// DB 저장 URL
PAGE_CONFIG["SAVE_URL"] = "/api/act/accounts";
// 목록 URL
PAGE_CONFIG["LIST_URL"] = "/api/act/accounts";
// 수정 버튼 함수, "" 시 기본 함수
PAGE_CONFIG["MOD_BTN"] = "mod";
// 수정 버튼 여부
PAGE_CONFIG["MOD_BTN_YN"] = "Y";
// 페이지 사이즈
PAGE_CONFIG["PAGING_YN"] = "N";
// 인덱스
PAGE_CONFIG["IDX"] = "actSeq";
//저장 함수
PAGE_CONFIG["INS_FUNC"] = "";

//테이블 구성 정보
var data = [];

data.push({"NM" : "ACT_SEQ_",	"VAL" : "actSeq",	"VAL2" : "",		"FUNC" : "goCd(this);",	"TYPE" : "T",	"OPT" : "O_R",	"TDST" : "T_C"});
data.push({"NM" : "BK_CD_",		"VAL" : "bkNm",		"VAL2" : "bkCd",	"FUNC" : "goCd(this);",	"TYPE" : "N",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "ACT_NM_",	"VAL" : "actNm",	"VAL2" : "",		"FUNC" : "goCd(this);",	"TYPE" : "T",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "ACT_CD_",	"VAL" : "actCdNm",	"VAL2" : "actCd",	"FUNC" : "goCd(this);",	"TYPE" : "N",	"OPT" : "",		"TDST" : "T_C"});
data.push({"NM" : "USR_NM_",	"VAL" : "usrNm",	"VAL2" : "usrId",	"FUNC" : "",	"TYPE" : "N",	"OPT" : "",		"TDST" : "T_C"});

$(document).ready(function () {
	ls_table_init(PAGE_CONFIG["LIST_URL"], 1, true);
	
	PAGE_CONFIG["BK_CD_SELECT"] = ct_cd_select("/api/codes/BK_CD", "cd", "cdNm");
	PAGE_CONFIG["ACT_CD_SELECT"] = ct_cd_select("/api/codes/ACT_CD", "cd", "cdNm");
	PAGE_CONFIG["USR_NM_SELECT"] = ct_cd_select("/api/users", "usrId", "usrNm");
});

function add(){
	// 기본 로직 처리 후 추가 처리
	ls_table_add();
	
	$("#TD_BK_CD_NEW").append(PAGE_CONFIG["BK_CD_SELECT"].replace("#ID#", "BK_CD_NEW"));
	$("#TD_ACT_CD_NEW").append(PAGE_CONFIG["ACT_CD_SELECT"].replace("#ID#", "ACT_CD_NEW"));
	$("#TD_USR_NM_NEW").append(PAGE_CONFIG["USR_NM_SELECT"].replace("#ID#", "USR_NM_NEW"));
}

function mod(id){
	ls_table_mod(id);
	
	var bkNm = $("#TD_BK_CD_" + id).text();
	var actCdNm = $("#TD_ACT_CD_" + id).text();
	var usrNm = $("#TD_USR_NM_" + id).text();
	
	$("#TD_BK_CD_" + id).empty();
	$("#TD_ACT_CD_" + id).empty();
	$("#TD_USR_NM_" + id).empty();
	
	$("#TD_BK_CD_" + id).append(PAGE_CONFIG["BK_CD_SELECT"].replace("#ID#", "BK_CD_" + id));
	$("#TD_ACT_CD_" + id).append(PAGE_CONFIG["ACT_CD_SELECT"].replace("#ID#", "ACT_CD_" + id));
	$("#TD_USR_NM_" + id).append(PAGE_CONFIG["USR_NM_SELECT"].replace("#ID#", "USR_NM_" + id));
	
	$("#BK_CD_" + id + " option:contains('" + bkNm + "')").attr("selected", "selected");
	$("#ACT_CD_" + id + " option:contains('" + actCdNm + "')").attr("selected", "selected");
	$("#USR_NM_" + id + " option:contains('" + usrNm + "')").attr("selected", "selected");
}

/**
 * 코드 목록
 */
function goCd(node){
	location.href="/act/trade.do?actSeq=" + $(node).parent().parent().children(":first").text();
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
							<h2>계좌 목록</h2>
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
										<col style="width: 20%;" />
										<col style="width: 20%;" />
										<col style="width: 20%;" />
										<col style="width: 20%;" />
										<col style="width: 10%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">일련번호</th>
											<th class="column-title" style="text-align: center;">은행명</th>
											<th class="column-title" style="text-align: center;">계좌명</th>
											<th class="column-title" style="text-align: center;">계좌구분</th>
											<th class="column-title" style="text-align: center;">소유주</th>
											<th class="column-title" style="text-align: center;">-</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
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