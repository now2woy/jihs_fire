<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
//페이지 설정 정보
var PAGE_CONFIG = [];
// DB 저장 URL
PAGE_CONFIG["SAVE_URL"] = "/api/code/columns";
// 목록 URL
PAGE_CONFIG["LIST_URL"] = "/api/code/columns";
// 수정 버튼 함수, "" 시 기본 함수
PAGE_CONFIG["MOD_BTN"] = "";
// 수정 버튼 여부
PAGE_CONFIG["MOD_BTN_YN"] = "Y";
//페이지 사이즈
PAGE_CONFIG["PAGING_YN"] = "N";
//인덱스
PAGE_CONFIG["IDX"] = "cdCol";

// 테이블 구성 정보
var data = [];

data.push({"NM" : "CD_COL_",	"VAL" : "cdCol",	"VAL2" : "",	"FUNC" : "goCd(this);",	"TYPE" : "T",	"OPT" : "",	"TDST" : ""});
data.push({"NM" : "CD_COL_NM_",	"VAL" : "cdColNm",	"VAL2" : "",	"FUNC" : "goCd(this);",	"TYPE" : "T",	"OPT" : "",	"TDST" : ""});
data.push({"NM" : "USE_YN_",	"VAL" : "useYn",	"VAL2" : "",	"FUNC" : "",			"TYPE" : "S",	"OPT" : "",	"TDST" : "T_C"});

$(document).ready(function () {
	ls_table_init(PAGE_CONFIG["LIST_URL"], 1);
});

/**
 * 코드 목록
 */
function goCd(node){
	location.href="/bsc/code.do?cdCol=" + $(node).parent().parent().children(":first").text();
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
							<h2>코드 컬럼 목록</h2>
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
										<col style="width: 35%;" />
										<col style="width: 35%;" />
										<col style="width: 15%;" />
										<col style="width: 15%;" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th class="column-title" style="text-align: center;">컬럼</th>
											<th class="column-title" style="text-align: center;">컬럼명</th>
											<th class="column-title" style="text-align: center;">사용여부</th>
											<th class="column-title" style="text-align: center;">-</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							
						<!-- 버튼 영역 시작 -->
							<div id="btn_div" style="float: right;">
								<button type="button" id="add-btn" class="btn btn-success" onclick="ls_table_add();">등록</button>
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