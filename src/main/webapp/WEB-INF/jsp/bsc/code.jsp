<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">

//저장 함수에서 사용하는 경로
var SAVE_URL = "/api/codes";

//테이블 구성 정보
var data = [];

data.push({"NM" : "CD_COL_",	"IDX" : "cd",	"VAL" : "cdCol",	"FUNC" : "",	"TYPE" : "text",	"OPT" : " readonly=\"readonly\""});
data.push({"NM" : "CD_",		"IDX" : "cd",	"VAL" : "cd",		"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "CD_NM_",		"IDX" : "cd",	"VAL" : "cdNm",		"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "ORD_",		"IDX" : "cd",	"VAL" : "ord",		"FUNC" : "",	"TYPE" : "text",	"OPT" : ""});
data.push({"NM" : "USE_YN_",	"IDX" : "cd",	"VAL" : "useYn",	"FUNC" : "",	"TYPE" : "select",	"OPT" : ""});

$(document).ready(function () {
	var params = new URL(location.href).searchParams;
	var url = "/api/codes/" + params.get("cdCol");
	
	ls_table_init(url);
});

/**
 * 등록 버튼
 */
function add(){
	// 기본 로직 처리 후 추가 처리
	ls_table_add();
	
	var params = new URL(location.href).searchParams;
	
	$("#CD_COL_NEW").val(params.get("cdCol"));
}

/**
 * 목록 버튼
 */
function list() {
	location.href="/bsc/column.do";
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
			<div class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>
					
					<div class="row" style="display: block; min-height: 880px;">
						<div class="col-md-12 col-sm-12  ">
							<div class="x_panel">
								<div class="x_title">
									<h2>코드 목록</h2>
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
												<col style="width: 15%;" />
												<col style="width: 15%;" />
												<col style="width: 40%;" />
												<col style="width: 10%;" />
												<col style="width: 10%;" />
												<col style="width: 10%;" />
											</colgroup>
											<thead>
												<tr class="headings">
													<th class="column-title" style="text-align: center;">컬럼</th>
													<th class="column-title" style="text-align: center;">코드</th>
													<th class="column-title" style="text-align: center;">코드명</th>
													<th class="column-title" style="text-align: center;">순번</th>
													<th class="column-title" style="text-align: center;">사용여부</th>
													<th class="column-title" style="text-align: center;">-</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								
							<!-- 버튼 영역 시작 -->
								<div style="float: right;">
									<button type="button" id="list-btn" class="btn btn-secondary" onclick="list();">목록</button>
									<button type="button" id="add-btn" class="btn btn-success" onclick="add();">등록</button>
								</div>
							<!-- 버튼 영역 종료 -->
								
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